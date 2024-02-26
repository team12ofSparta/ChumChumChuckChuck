package com.example.sparta.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPasswordUpdateResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String address;


}