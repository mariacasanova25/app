package com.example.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank
    @NonNull
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank
    @NonNull
    private String email;

    @NotBlank
    @NonNull
    private String password;

}
