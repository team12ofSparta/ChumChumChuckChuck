package com.example.sparta.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class UserSignupResponseDto {

    private String name;
    private String email;
    private String address;

}
