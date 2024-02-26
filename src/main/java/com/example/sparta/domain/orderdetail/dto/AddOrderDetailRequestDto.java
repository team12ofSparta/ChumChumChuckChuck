package com.example.sparta.domain.orderdetail.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AddOrderDetailRequestDto {

    private Integer quantity;

    public AddOrderDetailRequestDto(Integer quantity) {
        this.quantity = quantity;
    }
}
