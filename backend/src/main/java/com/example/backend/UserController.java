package com.example.backend;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.GeneratePasswordOtpRequest;
import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.RegisterStep1Request;
import com.example.backend.dto.UserProfileResponse;
import com.example.backend.dto.VerifyOtpRegisterRequest;
import com.example.backend.dto.VerifyPasswordOtpRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.services.EmailService;
import com.example.backend.services.JwtService;
import com.example.backend.services.LoginService;
import com.example.backend.services.UserService;

import brevo.ApiException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/details")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserProfile(authentication));
    }

    @PostMapping(
        value = "/register/step1",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> registerStep1(

        @RequestPart("data")
        RegisterStep1Request request,

        @RequestPart(value = "image", required = false)
        MultipartFile image

    ) throws Exception {

        return ResponseEntity.ok( userService.registerStep1(request, image) );

    }

    @PostMapping("/register/verify")
    public ResponseEntity<?> verifyOtp(

        @RequestBody
        VerifyOtpRegisterRequest request

    ) {

        return ResponseEntity.ok(userService.verifyOtpAndRegister(request));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        User user = loginService.login(request);

        String token = jwtService.generateToken(user.getEmail());

        return ResponseEntity.ok(
            Map.of(
                "token", token,
                "id", user.getId(),
                "email", user.getEmail(),
                "username", user.getUsername()
            )
        );

    }

    @PutMapping(
        value = "/update",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<User> updateProfile(
        Authentication authentication,

        @RequestParam(required = false)
        String username,

        @RequestParam(required = false)
        String email,

        @RequestParam(required = false)
        String phoneNumber,

        @RequestParam(required = false)
        MultipartFile profileImage
    ) throws IOException {

        User user = userService.updateProfile(authentication, username, email, phoneNumber, profileImage);

        return ResponseEntity.ok(user);

    }

    @PostMapping("/change-password/generate-otp")
    public ResponseEntity<?> generatePasswordOtp(Authentication authentication, @RequestBody GeneratePasswordOtpRequest request) throws ApiException {

        String resetToken = userService.generatePasswordChangeOtp(authentication, request);

        return ResponseEntity.ok(resetToken);

    }

    @PostMapping("/change-password/verify")
    public ResponseEntity<?> changePassword(Authentication authentication, @RequestBody VerifyPasswordOtpRequest request) {

        String response = userService.changePassword(authentication, request);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/test-email")
    public String testEmail() {
        try {

            emailService.sendEmail(
                "sahillutade27@gmail.com",
                "Verify OTP",
                "123456"
            );

            return "Email sent successfully";

        } catch (Exception e) {

            e.printStackTrace();

            return "Error: " + e.getMessage();

        }
    }

}
