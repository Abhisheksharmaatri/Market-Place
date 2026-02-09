package org.abhishek.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserRequest {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 4, max = 10, message = "Name cannot be shorter than 4 and longer than 10")
    private String name;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Mail cannot be empty")
    private String mail;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password needs to be at least 6 characters long")
    private String password;

    @NotBlank(message = "Role cannot be empty")
    private String role;
}
