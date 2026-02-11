package org.abhishek.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.config.JwtUtil;
import org.abhishek.dto.LoginRequest;
import org.abhishek.exception.BadRequestException;
import org.abhishek.exception.ConflictException;
import org.abhishek.exception.DatabaseException;
import org.abhishek.exception.ResourceNotFoundException;
import org.abhishek.model.User;
import org.abhishek.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String login(LoginRequest request) {
        log.info("Started logging with email id :{}",request.getMail());

        try{
            User user = userRepository.findByMail(request.getMail().toLowerCase())
                    .orElseThrow(() -> new RuntimeException("Invalid credentials"));

            log.info("Found the user for login for email id: {}", request.getMail());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }
            log.info("Generated the login token for email id: {}", request.getMail());

            return jwtUtil.generateToken(user);
        }catch (WebClientResponseException.NotFound ex) {
            log.info("User service resource not found");
            throw new ResourceNotFoundException("User service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            log.info("Invalid request sent to User service");
            throw new BadRequestException("Invalid request sent to User service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            log.info("User service unavailable");
            throw new DatabaseException("User service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            log.info("User violates database constraints");
            throw new ConflictException("User violates database constraints");
        }
        catch (DataAccessException ex) {
            log.info("Database operation failed");
            throw new DatabaseException("Database operation failed");
        }
    }
}

