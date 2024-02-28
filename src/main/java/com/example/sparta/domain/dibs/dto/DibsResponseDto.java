package com.example.sparta.domain.dibs.dto;

import com.example.sparta.domain.dibs.entity.Dibs;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DibsResponseDto {

    private Long userId;
    private String userName;

    private Long storeId;
    private String storeName;

    public DibsResponseDto(Dibs dibs) {
        userId = dibs.getUser().getUserId();
        userName = dibs.getUser().getName();
        storeId = dibs.getStore().getStoreId();
        storeName = dibs.getStore().getName();
    }
}
