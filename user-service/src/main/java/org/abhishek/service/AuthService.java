package org.abhishek.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.config.JwtUtil;
import org.abhishek.dto.LoginRequest;
import org.abhishek.model.User;
import org.abhishek.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginRequest request) {
        log.info("Started logging with email id :{}",request.getMail());

        User user = userRepository.findByMail(request.getMail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        log.info("Found the user for login for email id: {}", request.getMail());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        log.info("Generated the login token for email id: {}", request.getMail());

        return jwtUtil.generateToken(user);
    }
}

