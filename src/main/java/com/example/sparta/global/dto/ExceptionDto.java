package com.example.sparta.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ExceptionDto {

    private Integer statusCode;
    private HttpStatus state;
    private String message;

}
