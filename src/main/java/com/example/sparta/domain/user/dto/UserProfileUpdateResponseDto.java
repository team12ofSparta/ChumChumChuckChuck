package com.example.sparta.domain.user.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileUpdateResponseDto {

    private String name;
    private String email;
    private String address;

}
