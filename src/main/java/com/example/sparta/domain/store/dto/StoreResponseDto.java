package com.example.sparta.domain.store.dto;


import com.example.sparta.domain.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreResponseDto {
    private Long storeId;

    private String name;

    private String category;

    private String address;

    private String content;

    private Float rating;

    private Integer dibsCount;

    private Integer reviewCount;

    private String deliveryAddress;

    public  StoreResponseDto(Store store){
        this.storeId = store.getStoreId();
        this.deliveryAddress = store.getDeliveryAddress();
        this.name = store.getName();
        this.rating = store.getRating();
        this.address = store.getAddress();
        this.category = store.getCategory();
        this.dibsCount = store.getDibsCount();
        this.reviewCount = store.getReviewCount();
        this.content = store.getContent();
    }
}
