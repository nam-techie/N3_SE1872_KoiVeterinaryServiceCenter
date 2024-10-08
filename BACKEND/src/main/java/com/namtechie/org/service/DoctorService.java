package com.namtechie.org.service;


import com.namtechie.org.entity.Account;
import com.namtechie.org.entity.Doctor;
import com.namtechie.org.entity.Role;
import com.namtechie.org.model.UpdateDoctorLogin;
import com.namtechie.org.model.request.DoctorRequest;
import com.namtechie.org.repository.AccountRepository;
import com.namtechie.org.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    public Account getCurrentAccount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountById(account.getId());
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public void deleteDoctor(long id) {
        try {
            Doctor deleteDoctor = doctorRepository.findDoctorById(id);
            if (deleteDoctor == null) {
                throw new RuntimeException("Không có thông tin của bác sĩ bạn cần tìm!");
            }
            doctorRepository.delete(deleteDoctor);
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình xóa thông tin bác sĩ. Vui lòng thử lại sau.");
        }
    }

    public Doctor getDoctorById(long id) {
        try {
            Doctor doctor = doctorRepository.findDoctorById(id);
            if (doctor == null) {
                throw new RuntimeException("Không có thông tin của bác sĩ bạn cần tìm!");
            }
            return doctor;
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình tìm kiếm thông tin bác sĩ. Vui lòng thử lại sau.");
        }
    }

    public Doctor addInfoVeterinary(DoctorRequest doctorRequest) {
        try {
            // Lấy tài khoản hiện tại của người dùng đã xác thực
            Account currentAccount = getCurrentAccount();
            if (!currentAccount.getRole().equals(Role.VETERINARY.name())) {
                throw new RuntimeException("Chỉ tài khoản của bác sĩ mới có thể thực hiện hành động này.");
            }

            // Kiểm tra xem bác sĩ có tồn tại không, nếu không thì khởi tạo mới
            Doctor doctor = doctorRepository.findByAccountId(currentAccount.getId());
            if (doctor == null) {
                doctor = new Doctor();  // Khởi tạo đối tượng Doctor mới
                doctor.setAccount(currentAccount);  // Liên kết với tài khoản
            }

            // Xét trường hợp nếu user ko nhập gì thì ko update
            if (!doctorRequest.getFullName().equals(doctor.getFullname())) {
                doctor.setFullname(doctorRequest.getFullName());
            }
            if (!doctorRequest.getPhone().equals(doctor.getPhone())) {
                doctor.setPhone(doctorRequest.getPhone());
            }
            if (!doctorRequest.getSpecialty().equals(doctor.getSpecialty())) {
                doctor.setSpecialty(doctorRequest.getSpecialty());
            }
            if (!doctorRequest.getIntroduction().equals(doctor.getIntroduction())) {
                doctor.setIntroduction(doctorRequest.getIntroduction());
            }
            if (!doctorRequest.getTraining().equals(doctor.getTraining())) {
                doctor.setTraining(doctorRequest.getTraining());
            }
            if (!doctorRequest.getWorkExperience().equals(doctor.getWorkExperience())) {
                doctor.setWorkExperience(doctorRequest.getWorkExperience());
            }
            if (!doctorRequest.getAchievements().equals(doctor.getAchievements())) {
                doctor.setAchievements(doctorRequest.getAchievements());
            }
            if (!doctorRequest.getResearchPapers().equals(doctor.getResearchPapers())) {
                doctor.setResearchPapers(doctorRequest.getResearchPapers());
            }

            // Lưu đối tượng Doctor vào cơ sở dữ liệu
            return doctorRepository.save(doctor);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Tài khoản này đã có thông tin bác sĩ.");
        } catch (Exception e) {
            // Log lỗi hoặc xử lý các ngoại lệ khác nếu cần
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình thêm thông tin bác sĩ. Vui lòng thử lại sau.");
        }
    }
}
