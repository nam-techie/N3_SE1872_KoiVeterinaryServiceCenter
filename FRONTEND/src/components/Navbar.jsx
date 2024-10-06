import { Link } from "react-router-dom";
import logo from "../assets/images/logo.png";
import "../styles/Navbar.css";
import { useNavbar } from "../hooks/useNavbar.js";
import userIcon from "../assets/images/logo.png";
import { FaMapMarkerAlt, FaPhoneAlt, FaClock } from "react-icons/fa";

function Navbar() {
  const { isLoggedIn, showDropdown, setShowDropdown, handleLogout } =
    useNavbar();
  return (
    <>
  <div className="top-navbar">
    <div className="contact-info">
      <span><FaMapMarkerAlt /> Lô E2a-7, Đường D1, Đ. D1, Long Thạnh Mỹ, TP.Thủ Đức, HCM</span>
      <span><FaPhoneAlt /> 1800.999 (Miễn phí)</span>
      <span><FaClock /> 06:00 - 19:00 (Thứ Hai - Chủ Nhật)</span>
    </div>
  </div>

      <nav className="navbar">
        <div className="logo">
          <Link to="/homepage">
            <img src={logo} alt="logo" />
          </Link>
        </div>
        <ul className="nav-links">
          <li>
            <Link to="/homepage">Trang Chủ</Link>
          </li>
          <li>
            <Link to="/aboutme">Về KoiCung</Link>
          </li>
          <li>
            <Link to="/services">Dịch vụ</Link>
          </li>
          <li>
            <Link to="#">Tìm Bác Sĩ</Link>
          </li>
          <li>
            <Link to="/termandrefunds">Điều Khoản Và Dịch Vụ</Link>
          </li>
          <li>
            <Link to="#">Cẩm Nang</Link>
          </li>
          <li>
            <Link to="#">FAQ</Link>
          </li>
        </ul>

        <div className="auth-buttons">
          {isLoggedIn ? (
            <div className="user-menu">
              <img
                src={userIcon}
                alt="User"
                className="user-icon"
                onClick={() => setShowDropdown(!showDropdown)} // Thay đổi trạng thái dropdown khi bấm vào icon người dùng
              />
              {showDropdown && (
                <div className="dropdown-menu">
                  <Link to="/profile">Thông tin cá nhân</Link>
                  <Link to="/booking-history">Lịch sử đơn đặt</Link>
                  <button onClick={handleLogout}>Đăng xuất</button>
                </div>
              )}
            </div>
          ) : (
            <>
              <Link to="/login" className="btn login-btn">
                Đăng nhập
              </Link>
              <Link to="/register" className="btn register-btn">
                Đăng Kí
              </Link>
            </>
          )}
        </div>
      </nav>
    </>
  );
}

export default Navbar;
