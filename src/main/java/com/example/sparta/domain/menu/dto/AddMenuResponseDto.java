package com.example.sparta.domain.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddMenuResponseDto {

    private Long menuId;
    private String menuName;
    private Long menuPrice;
    private Long storeId;
    private String storeName;
}
