package com.example.sparta.domain.user.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileUpdateResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String address;
}