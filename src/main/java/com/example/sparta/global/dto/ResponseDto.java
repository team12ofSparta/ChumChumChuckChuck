package com.example.sparta.global.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto<T> {

    private Integer statusCode;
    private T data;

}