package com.example.sparta.domain.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateStoreRequestDto {

    private String name;
    private String category;
    private String address;
    private String content;
    private String deliveryAddress;

}
