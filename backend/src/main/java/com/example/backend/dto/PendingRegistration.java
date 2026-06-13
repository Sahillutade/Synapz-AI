package com.example.backend.dto;

import java.time.LocalDateTime;

public class PendingRegistration {

    private String username;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private String imagePath;
    private String otp;
    private LocalDateTime expiryTime;
    
    public PendingRegistration() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public PendingRegistration(String username, String email, String phoneNumber, String imageUrl, String imagePath, String otp,
            LocalDateTime expiryTime) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    

}
