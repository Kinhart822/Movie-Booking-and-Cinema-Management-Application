import React, { useState } from "react";
import axios from "axios";
import "./PasswordReset.css";

const PasswordReset = ({ onBackToLogin, onResetSuccess }) => {
  const [email, setEmail] = useState("");
  const [otp, setOtp] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [step, setStep] = useState(1);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleResetRequest = async () => {
    setError("");
    setSuccess("");

    if (!email) {
      setError("Vui lòng nhập email");
      return;
    }

    try {
      await axios.post(
        "http://10.147.17.110:8080/api/v1/auth/reset-password/request",
        {
          email,
          type: 1,
        }
      );
      setSuccess("OTP đã được gửi đến email của bạn!");
      setStep(2);
    } catch (err) {
      setError(
        err.response?.data?.description ||
          err.response?.data?.message ||
          "Có lỗi xảy ra!"
      );
    }
  };

  const handleResetFinish = async () => {
    setError("");
    setSuccess("");

    if (!otp || !newPassword) {
      setError("Vui lòng nhập đầy đủ thông tin");
      return;
    }

    try {
      await axios.post(
        "http://10.147.17.110:8080/api/v1/auth/reset-password/finish",
        {
          resetKey: otp,
          type: 1,
          newPassword,
        }
      );
      setSuccess("Mật khẩu đã được cập nhật thành công!");

      // After successful reset, navigate to login after a brief delay
      setTimeout(() => {
        onResetSuccess();
      }, 1500);
    } catch (err) {
      setError(
        err.response?.data?.description ||
          err.response?.data?.message ||
          "Có lỗi xảy ra!"
      );
    }
  };

  return (
    <div className="container">
      <div className="header">
        <div className="text">Đặt lại mật khẩu</div>
        <div className="underline"></div>
      </div>

      {error && <div className="message error">{error}</div>}
      {success && <div className="message success">{success}</div>}

      {step === 1 ? (
        <>
          <div className="inputs">
            <div className="input">
              <input
                type="email"
                placeholder="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
            </div>
          </div>
          <div className="submit-container">
            <div className="submit" onClick={handleResetRequest}>
              Gửi yêu cầu
            </div>
            <div className="submit gray" onClick={onBackToLogin}>
              Quay lại Đăng nhập
            </div>
          </div>
        </>
      ) : (
        <>
          <div className="inputs">
            <div className="input">
              <input
                type="text"
                placeholder="OTP"
                value={otp}
                onChange={(e) => setOtp(e.target.value)}
              />
            </div>
            <div className="input">
              <input
                type="password"
                placeholder="New Password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
              />
            </div>
          </div>
          <div className="submit-container">
            <div className="submit" onClick={handleResetFinish}>
              Cập nhật mật khẩu
            </div>
            <div className="submit gray" onClick={() => setStep(1)}>
              Quay lại bước trước
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default PasswordReset;
