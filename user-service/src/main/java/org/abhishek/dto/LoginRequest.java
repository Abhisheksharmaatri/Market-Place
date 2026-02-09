package org.abhishek.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @Email(message = "Invalid Email")
    @NotBlank(message = "Empty Email")
    private String mail;

    @NotBlank(message = "Empty Password")
    private String password;
}
