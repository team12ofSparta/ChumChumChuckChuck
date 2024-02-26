package com.example.sparta.domain.user.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    private Long userId;
    private String password;
    private String email;
    private String name;
    private String address;
    private String token;


}