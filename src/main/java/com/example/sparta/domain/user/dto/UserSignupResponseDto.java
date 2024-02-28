package com.example.sparta.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserSignupResponseDto {

    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String email;
    private String address;

}
