package com.example.sparta.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDetailResponseBucket {

    private Long orderDetailId;
    private Long menuId;
    private String menuName;
    private Integer menuQuantity;
}
