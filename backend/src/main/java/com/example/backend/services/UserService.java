package com.example.backend.services;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.FileUploadResponse;
import com.example.backend.dto.GeneratePasswordOtpRequest;
import com.example.backend.dto.PasswordResetSession;
import com.example.backend.dto.PendingRegistration;
import com.example.backend.dto.RegisterStep1Request;
import com.example.backend.dto.UserProfileResponse;
import com.example.backend.dto.VerifyOtpRegisterRequest;
import com.example.backend.dto.VerifyPasswordOtpRequest;
import com.example.backend.enums.AuthProvider;
import com.example.backend.model.User;
import com.example.backend.model.UserSubscription;
import com.example.backend.repository.UserRepository;
import com.example.backend.repository.UserSubscriptionRepository;

import brevo.ApiException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final OtpService otpService;
    private final SupabaseStorageService storageService;
    private final PasswordEncoder passwordEncoder;
    private final UserSubscriptionRepository userSubscriptionRepository;
    public UserService(UserRepository userRepository, EmailService emailService, OtpService otpService,
            SupabaseStorageService storageService, PasswordEncoder passwordEncoder, UserSubscriptionRepository userSubscriptionRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.otpService = otpService;
        this.storageService = storageService;
        this.passwordEncoder = passwordEncoder;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public UserProfileResponse getUserProfile(Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        UserSubscription subscription = userSubscriptionRepository.findByUser(user).orElse(null);

        UserProfileResponse response = new UserProfileResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setProfileImageUrl(user.getProfileImageUrl());

        if(subscription != null){
            response.setSubscriptionPackage(subscription.getSubscriptionPackage().getPackageName());
        }
        else{
            response.setSubscriptionPackage("No Active Subscription");
        }

        response.setPassword("********");

        return response;

    }

    public String registerStep1( RegisterStep1Request request, MultipartFile image ) throws Exception {

        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        String imageUrl = null;
        String imagePath = null;

        if(image != null && !!image.isEmpty()) {

            FileUploadResponse uploadResponse = storageService.uploadImage(image);

            imageUrl = uploadResponse.getImageUrl();
            imagePath = uploadResponse.getImagePath();

        }

        String otp = otpService.generateOtp();

        PendingRegistration pending = new PendingRegistration();

        pending.setUsername(request.getUsername());

        pending.setEmail(request.getEmail());

        pending.setPhoneNumber(request.getPhoneNumber());

        pending.setImageUrl(imageUrl);

        pending.setImagePath(imagePath);

        pending.setOtp(otp);

        pending.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        String registrationToken = otpService.createRegistrationToken();

        otpService.save(registrationToken, pending);

        String htmlContent = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8" />
                    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                    <title>Synapz AI \u2013 Email Verification</title>
                </head>
                <body style="margin:0;padding:0;background-color:#0a0c12;font-family:'Segoe UI',Helvetica,Arial,sans-serif;">

                    <!-- Preheader (hidden preview text) -->
                    <span style="display:none;max-height:0;overflow:hidden;mso-hide:all;">
                        Your Synapz AI verification code is ready. Use it within 5 minutes.
                    </span>

                    <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="background-color:#0a0c12;padding:40px 16px;">
                        <tr>
                            <td align="center">

                            <!-- Card -->
                            <table role="presentation" width="100%%" style="max-width:520px;background-color:#12151f;border-radius:16px;overflow:hidden;border:1px solid #1e2336;">

                                <!-- Top accent bar -->
                                <tr>
                                    <td style="height:4px;background:linear-gradient(90deg,#6c63ff 0%%,#00d4ff 50%%,#7b2fff 100%%);"></td>
                                </tr>

                                <!-- Header -->
                                <tr>
                                    <td align="center" style="padding:36px 40px 24px;">
                                        <table role="presentation" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td style="background:linear-gradient(135deg,#6c63ff,#00d4ff);border-radius:12px;padding:10px 14px;">
                                                    <span style="font-size:20px;font-weight:800;color:#ffffff;letter-spacing:-0.5px;">S</span>
                                                </td>
                                                <td style="padding-left:10px;vertical-align:middle;">
                                                    <span style="font-size:22px;font-weight:700;color:#ffffff;letter-spacing:-0.3px;">Synapz <span style="color:#6c63ff;">AI</span></span>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <!-- Body -->
                                <tr>
                                    <td style="padding:0 40px 36px;">

                                        <p style="margin:0 0 8px;font-size:13px;font-weight:600;color:#6c63ff;text-transform:uppercase;letter-spacing:1.5px;">Email Verification</p>
                                        <h1 style="margin:0 0 16px;font-size:26px;font-weight:700;color:#f0f2ff;line-height:1.3;">Verify your email address</h1>
                                        <p style="margin:0 0 32px;font-size:15px;color:#8b90a8;line-height:1.7;">
                                            Welcome to Synapz AI. Use the one-time code below to complete your verification. This code expires in <strong style="color:#f0f2ff;">5 minutes</strong>.
                                        </p>

                                        <!-- OTP Box -->
                                        <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="margin-bottom:32px;">
                                            <tr>
                                                <td align="center" style="background:linear-gradient(135deg,rgba(108,99,255,0.12),rgba(0,212,255,0.08));border:1px solid rgba(108,99,255,0.35);border-radius:12px;padding:28px 20px;">
                                                    <p style="margin:0 0 8px;font-size:11px;font-weight:600;color:#6c63ff;text-transform:uppercase;letter-spacing:2px;">Your OTP Code</p>
                                                    <p style="margin:0;font-size:46px;font-weight:800;color:#ffffff;letter-spacing:14px;font-variant-numeric:tabular-nums;">%s</p>
                                                </td>
                                            </tr>
                                        </table>

                                        <!-- Security note -->
                                        <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="margin-bottom:28px;">
                                            <tr>
                                                <td style="background-color:#0e1118;border-left:3px solid #f59e0b;border-radius:0 8px 8px 0;padding:14px 16px;">
                                                    <p style="margin:0;font-size:13px;color:#9ca3af;line-height:1.6;">
                                                    \uD83D\uDD12 <strong style="color:#f0f2ff;">Never share this code</strong> with anyone. Synapz AI will never ask for your OTP via email or phone.
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>

                                        <p style="margin:0;font-size:14px;color:#6b7280;line-height:1.7;">
                                            If you didn't request this code, you can safely ignore this email. Your account remains secure.
                                        </p>

                                    </td>
                                </tr>

                                <!-- Divider -->
                                <tr>
                                    <td style="padding:0 40px;">
                                        <div style="height:1px;background-color:#1e2336;"></div>
                                    </td>
                                </tr>

                                <!-- Footer -->
                                <tr>
                                    <td style="padding:24px 40px 32px;">
                                        <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td>
                                                    <p style="margin:0 0 4px;font-size:12px;color:#4b5268;">&copy; 2025 Synapz AI. All rights reserved.</p>
                                                    <p style="margin:0;font-size:12px;color:#4b5268;">This is an automated message &mdash; please do not reply.</p>
                                                </td>
                                                <td align="right" valign="middle">
                                                    <span style="font-size:11px;font-weight:700;letter-spacing:1px;text-transform:uppercase;color:#6c63ff;">Powered by AI</span>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                            </table>

                        </td>
                    </tr>
                </table>

            </body>
            </html>
            """
        .formatted(otp);

        emailService.sendEmail(
            request.getEmail(), 
            "Verify Your Email", 
            htmlContent);
            
        return registrationToken;

    }

    public String verifyOtpAndRegister(VerifyOtpRegisterRequest request) {

        PendingRegistration pending = otpService.get(request.getRegistrationToken());

        if(pending == null) {
            throw new RuntimeException("Registration session expired");
        }

        if(LocalDateTime.now().isAfter(pending.getExpiryTime())) {
            otpService.remove(request.getRegistrationToken());

            throw new RuntimeException("OTP expired");
        }

        if(!pending.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        User user = new User();

        user.setUsername(pending.getUsername());

        user.setEmail(pending.getEmail());

        user.setPhoneNumber(pending.getPhoneNumber());

        user.setProfileImageUrl(pending.getImageUrl());

        user.setProfileImagePath(pending.getImagePath());

        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        user.setAuthProvider(AuthProvider.LOCAL);

        user.setEmailVerified(true);

        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        otpService.remove(request.getRegistrationToken());

        return "Registration Successful";

    }

    public User updateProfile(
        Authentication authentication,
        String username,
        String email,
        String phonenumber,
        MultipartFile profileImage
    ) throws IOException {

        String currentEmail = authentication.getName();

        User user = userRepository.findByEmail(currentEmail).orElseThrow(() -> new RuntimeException("User not found"));

        if(username != null && !username.isBlank()) {
            user.setUsername(username);
        }

        if(phonenumber != null && !phonenumber.isBlank()) {
            user.setPhoneNumber(phonenumber);
        }

        if(email != null && !email.isBlank()) {

            if(!email.equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(email)) {

                throw new RuntimeException("Email already exists");

            }

            user.setEmail(email);

        }

        if(profileImage != null && !profileImage.isEmpty()) {

            if(user.getProfileImagePath() != null) {

                storageService.deleteImage(user.getProfileImagePath());

            }

            FileUploadResponse uploadResponse = storageService.uploadImage(profileImage);

            user.setProfileImageUrl(uploadResponse.getImageUrl());

            user.setProfileImagePath(uploadResponse.getImagePath());

        }

        return userRepository.save(user);

    }

    public String generatePasswordChangeOtp(Authentication authentication, GeneratePasswordOtpRequest request) throws ApiException {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {
            throw new RuntimeException("New password cannot be same as current password");
        }

        String otp = otpService.generateOtp();

        PasswordResetSession session = new PasswordResetSession();

        session.setEmail(user.getEmail());
        session.setOtp(otp);
        session.setEncodedPassword(passwordEncoder.encode(request.getNewPassword()));
        session.setExpiryTime(LocalDateTime.now().plusMinutes(5));

        String resetToken = otpService.createPasswordResetToken();

        otpService.savePasswordReset(resetToken, session);

        emailService.sendEmail(
            user.getEmail(),
            "Password Change OTP",
            "Your OTP is: " + otp
        );

        return resetToken;

    }

    public String changePassword(Authentication authentication, VerifyPasswordOtpRequest request) {

        PasswordResetSession session = otpService.getPasswordReset(request.getResetToken());

        if(session == null) {
            throw new RuntimeException("Session expired");
        }

        if(LocalDateTime.now().isAfter(session.getExpiryTime())) {
            otpService.removePasswordReset(request.getResetToken());
            throw new RuntimeException("OTP expired");
        }

        if(!session.getOtp().equals(request.getOtp())) {

            throw new RuntimeException("Invalid OTP");

        }

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setPasswordHash(session.getEncodedPassword());

        userRepository.save(user);

        otpService.removePasswordReset(request.getResetToken());

        return "Password changed successsfully";

    }

}
