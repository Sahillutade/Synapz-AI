package com.example.backend.services;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.backend.dto.PasswordResetSession;
import com.example.backend.dto.PendingRegistration;

@Service
public class OtpService {

    private final Map<String, PendingRegistration> registrations = new ConcurrentHashMap<>();

    public String generateOtp(){

        return String.valueOf(
            ThreadLocalRandom.current().nextInt(100000, 999999)
        );

    }

    public String createRegistrationToken(){
        return UUID.randomUUID().toString();
    }

    public void save(
        String token,
        PendingRegistration registration
    ){
        registrations.put(token, registration);
    }

    public PendingRegistration get(
        String token
    ){
        return registrations.get(token);
    }

    public void remove(String token){
        registrations.remove(token);
    }

    private final Map<String, PasswordResetSession> passwordResetStore = new ConcurrentHashMap<>();

    public String createPasswordResetToken() {
        return UUID.randomUUID().toString();
    }

    public void savePasswordReset(
        String token,
        PasswordResetSession session
    ) {

        passwordResetStore.put(token, session);

    }

    public PasswordResetSession getPasswordReset(String token) {
        return passwordResetStore.get(token);
    }

    public void removePasswordReset(String token) {
        passwordResetStore.remove(token);
    }

}
