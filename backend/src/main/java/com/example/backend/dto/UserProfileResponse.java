package com.example.backend.dto;

import java.util.UUID;

public class UserProfileResponse {

    private UUID id;
    private String username;
    private String email;
    private String profileImageUrl;
    private String subscriptionPackage;
    private String password;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
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
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
    public String getSubscriptionPackage() {
        return subscriptionPackage;
    }
    public void setSubscriptionPackage(String subscriptionPackage) {
        this.subscriptionPackage = subscriptionPackage;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
