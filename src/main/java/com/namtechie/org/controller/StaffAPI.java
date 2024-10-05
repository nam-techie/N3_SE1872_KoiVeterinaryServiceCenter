package com.namtechie.org.controller;

import com.namtechie.org.dto.ServiceRequestDTO;
import com.namtechie.org.entity.ServiceRequest;
import com.namtechie.org.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StaffAPI {
    @Autowired
    ServiceRequestService serviceRequestService;
     @PostMapping("/request_service")
    public ResponseEntity createServiceRequests(@RequestBody ServiceRequestDTO serviceRequestDTO) {
         ServiceRequest serviceRequest = serviceRequestService.convertDTOtoEntity(serviceRequestDTO);
         ServiceRequest request = serviceRequestService.createServiceRequests(serviceRequest);
                 return ResponseEntity.ok(request);
     }


}
