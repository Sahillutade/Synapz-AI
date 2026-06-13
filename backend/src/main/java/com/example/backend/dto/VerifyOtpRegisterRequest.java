package com.example.backend.dto;

public class VerifyOtpRegisterRequest {

    private String registrationToken;
    private String otp;
    private String password;

    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public VerifyOtpRegisterRequest() {
    }
    public VerifyOtpRegisterRequest(String registrationToken,String otp, String password) {
        this.registrationToken = registrationToken;
        this.otp = otp;
        this.password = password;
    }
    public String getRegistrationToken() {
        return registrationToken;
    }
    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }
    

}
