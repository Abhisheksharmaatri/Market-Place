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
        log.info("reaching service");
        try{
            User user=mapFromRequest(userRequest);
            User savedUser=userRepository.save(user);
        }
        catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Product service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to product service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Product service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
            throw new DatabaseException("Database operation failed");
        }
    };
    public void Update(){};
    public void Delete(){};

    public UserResponse Get(String mail){
        try{
            User user=userRepository.findByMail(mail)
                    .orElseThrow(()-> new ResourceNotFoundException("The user with this email was not found."));
            UserResponse userResponse=mapToResponse(user);
            return userResponse;
        }
        catch (WebClientResponseException.NotFound ex) {
            throw new ResourceNotFoundException("Product service resource not found");
        }
        catch (WebClientResponseException.BadRequest ex) {
            throw new BadRequestException("Invalid request sent to product service");
        }
        catch (WebClientResponseException.ServiceUnavailable ex) {
            throw new DatabaseException("Product service unavailable");
        }
        catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Product violates database constraints");
        }
        catch (DataAccessException ex) {
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
