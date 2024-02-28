package com.example.sparta.domain.orderdetail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOrderDetailMenuResponseDto {

    private Long menuId;
    private String menuName;
    private Long menuPrice;
    private Integer quantity;
}
