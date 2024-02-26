package com.example.sparta.domain.store.entity;

import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.global.entity.Timestamped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "stores")
public class Store extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user")
    private User owner;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String address;

    @Column
    private String content;

    @Column(nullable = false)
    private Float rating;

    @Column(nullable = false)
    private Integer dibsCount;

    @Column(nullable = false)
    private Integer reviewCount;

    @Column
    private String deliveryAddress;

    public Store(StoreRequestDto requestDto,User user){
        name = requestDto.getName();
        owner = user;
        category = requestDto.getCategory();
        address = requestDto.getAddress();
        content = requestDto.getContent();
        rating = requestDto.getRating();
        dibsCount = requestDto.getDibsCount();
        reviewCount = requestDto.getReviewCount();
        deliveryAddress = requestDto.getDeliveryAddress();
    }
    @Transactional
    public void update(StoreRequestDto requestDto) {
        name = requestDto.getName();
        category = requestDto.getCategory();
        address = requestDto.getAddress();
        content = requestDto.getContent();
        rating = requestDto.getRating();
        dibsCount = requestDto.getDibsCount();
        reviewCount = requestDto.getReviewCount();
        deliveryAddress = requestDto.getDeliveryAddress();
    }
}