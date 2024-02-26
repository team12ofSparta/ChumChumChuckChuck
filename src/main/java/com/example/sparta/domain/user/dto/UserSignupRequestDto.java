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
public class UserSignupRequestDto {

    @NotBlank
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;
    // 영문,숫자로 이루어진 8~15글자의 비밀번호

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String address;
}