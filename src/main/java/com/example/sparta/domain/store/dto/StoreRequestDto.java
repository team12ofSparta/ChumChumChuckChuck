package com.example.sparta.domain.store.dto;

import lombok.Getter;

@Getter
public class StoreRequestDto {
    private String name;
    private String category;
    private String address;
    private String content;
    private Float rating;
    private Integer dibsCount;
    private Integer reviewCount;
    private String deliveryAddress;
}
