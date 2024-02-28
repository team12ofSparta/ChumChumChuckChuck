package com.example.sparta.domain.user.controller;

import com.example.sparta.global.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ExceptionDto> handleException(IllegalArgumentException e){
        ExceptionDto exceptionDto = ExceptionDto.builder()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .state(HttpStatus.BAD_REQUEST)
            .message(e.getMessage())
            .build();
        return new ResponseEntity<>(exceptionDto,HttpStatus.BAD_REQUEST);
    }

}