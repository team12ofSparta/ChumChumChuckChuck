package com.example.sparta.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
//Builder
//@JsonDeserialize(builder=CreateOrderRequestDto.CreateOrderRequestDtoBuilder.class)
public class CreateOrderRequestDto {

    private String requests;
}
