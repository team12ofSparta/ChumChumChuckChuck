package com.example.sparta.domain.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetMenuResponseDto {

    private Long menuId;
    private String menuName;
    private Long menuPrice;
}
