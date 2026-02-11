package org.abhishek.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.dto.UserRequest;
import org.abhishek.dto.UserResponse;
import org.abhishek.exception.BadRequestException;
import org.abhishek.exception.ConflictException;
import org.abhishek.exception.DatabaseException;
import org.abhishek.exception.ResourceNotFoundException;
import org.abhishek.model.Role;
import org.abhishek.model.User;
import org.abhishek.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void Create(UserRequest userRequest){
        log.info("Started CREATE request for User service.");
        try{
            User user=mapFromRequest(userRequest);
            log.info("Saving the User Object for User CREATE request.");
            User savedUser=userRepository.save(user);
        }
        catch (WebClientResponseException.NotFound ex) {
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
    };
    public void Update(){};
    public void Delete(){};

    public UserResponse Get(String mail){
        log.info("Started the GET request for User service for email id: {}", mail);
        try{
            User user=userRepository.findByMail(mail)
                    .orElseThrow(()-> new ResourceNotFoundException("The user with this email was not found."));
            log.info("User for GET request for User service was found for email id: {}", mail);
            UserResponse userResponse=mapToResponse(user);
            log.info("Returning the user response for GET request for User service with email id: {}.", mail);
            return userResponse;
        }
        catch (WebClientResponseException.NotFound ex) {
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

//    Helpers

    private User mapFromRequest(UserRequest userRequest) {

        // FIX 1: Safe role mapping using enum
        Role role;
        if ("ADMIN".equalsIgnoreCase(userRequest.getRole())) {
            role = Role.Admin;
        } else {
            role = Role.User; // default role
        }

        // FIX 2: Password encryption using BCrypt
        String encryptedPassword =
                passwordEncoder.encode(userRequest.getPassword());

        // FIX 3: Correct builder usage
        User user = User.builder()
                .name(userRequest.getName())
                .mail(userRequest.getMail().toLowerCase())
                .role(role)
                .password(encryptedPassword)   // encrypted password
                .build();

        return user;
    }

    private UserResponse mapToResponse(User user){
        String role;
        if(user.getRole()==Role.Admin){
            role="Admin";
        }else{
            role="User";
        }
        UserResponse userResponse=UserResponse
                .builder()
                .name(user.getName())
                .mail(user.getMail())
                .id(user.getId())
                .role(role)
                .build();
        return userResponse;
    }
}
