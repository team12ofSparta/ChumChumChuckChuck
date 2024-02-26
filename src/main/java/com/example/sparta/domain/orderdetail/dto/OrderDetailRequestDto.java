package com.example.sparta.domain.orderdetail.dto;

import lombok.Getter;

@Getter
public class OrderDetailRequestDto {

    private Integer quantity;

    public OrderDetailRequestDto(Integer quantity) {
        this.quantity = quantity;
    }
}
