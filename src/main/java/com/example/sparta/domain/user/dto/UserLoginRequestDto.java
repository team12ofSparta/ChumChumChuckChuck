package com.example.sparta.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;

}