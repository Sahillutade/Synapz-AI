package com.example.backend.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.backend.enums.AuthProvider;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.services.JwtService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");

        String name = oauthUser.getAttribute("name");

        String picture = oauthUser.getAttribute("picture");

        String googleId = oauthUser.getAttribute("sub");

        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;

        if(existingUser.isPresent()) {

            user = existingUser.get();

            if(user.getGoogleId() == null) {

                user.setGoogleId(googleId);

                user.setAuthProvider(AuthProvider.GOOGLE);

                if(user.getProfileImageUrl() == null || user.getProfileImageUrl().isBlank()) {

                    user.setProfileImageUrl(picture);

                }

                userRepository.save(user);

            }

        }
        else {

            user  = new User();

            user.setUsername(name);

            user.setEmail(email);

            user.setGoogleId(googleId);

            user.setProfileImageUrl(picture);

            user.setEmailVerified(true);

            user.setAuthProvider(AuthProvider.GOOGLE);

            user.setCreatedAt(LocalDateTime.now());

            user.setUpdatedAt(LocalDateTime.now());

            userRepository.save(user);

        }

        String token = jwtService.generateToken(user.getEmail());

        response.sendRedirect("http://localhost:5173/#/login-success?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8));

    }

}
