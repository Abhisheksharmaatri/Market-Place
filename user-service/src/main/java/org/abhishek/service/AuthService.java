package org.abhishek.service;

import lombok.RequiredArgsConstructor;
import org.abhishek.config.JwtUtil;
import org.abhishek.dto.LoginRequest;
import org.abhishek.model.User;
import org.abhishek.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginRequest request) {

        User user = userRepository.findByMail(request.getMail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }
}

