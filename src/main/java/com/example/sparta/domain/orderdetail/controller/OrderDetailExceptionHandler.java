package com.example.sparta.domain.orderdetail.controller;

import com.example.sparta.global.dto.ExceptionDto;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderDetailExceptionHandler {

    @ExceptionHandler({NullPointerException.class, NoSuchElementException.class})
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(
            ExceptionDto.builder().statusCode(HttpStatus.NOT_FOUND.value())
                .state(HttpStatus.NOT_FOUND).message(ex.getMessage()).build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDto> handleException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(
            ExceptionDto.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                .state(HttpStatus.BAD_REQUEST).message(ex.getMessage()).build());
    }
}
