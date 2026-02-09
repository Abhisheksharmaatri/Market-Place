package org.abhishek.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abhishek.config.AuthService;
import org.abhishek.dto.LoginRequest;
import org.abhishek.dto.LoginResponse;
import org.abhishek.dto.UserRequest;
import org.abhishek.dto.UserResponse;
import org.abhishek.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void Create(@Valid @RequestBody UserRequest userRequest){

        log.info("controller");
        userService.Create(userRequest);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public String Status(){
        return "Running";
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserResponse Get(
            @RequestParam("mail") String mail
    ){
        return userService.Get(mail);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login(
             @Valid @RequestBody LoginRequest request) {
        log.info("Repeated11111111111111");
        String token = authService.login(request);
        log.info("Repeated22222222222222222");
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
