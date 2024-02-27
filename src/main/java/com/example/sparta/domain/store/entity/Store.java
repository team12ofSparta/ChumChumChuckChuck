package com.example.sparta.domain.store.entity;

import com.example.sparta.domain.store.dto.OpeningHoursDto;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.global.entity.Timestamped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import java.time.LocalTime;
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
    @JoinColumn(name = "user_id")
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

    @Column
    private StoreStatus status;

    @Column
    private LocalTime openingTime;
    @Column
    private LocalTime closingTime;

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

        status = StoreStatus.PREPARING;
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
    @Transactional
    public void openStore(boolean b){
        status = b?StoreStatus.RUNNING:StoreStatus.CLOSED;
    }
    @Transactional
    public void setOpeningHours(OpeningHoursDto dto){
        openingTime = dto.getOpening();
        closingTime = dto.getClosing();
    }
    // 관리자 권한
    @Transactional
    public void updateStatus(int input){
        switch (input) {
            case 1 -> this.status = StoreStatus.PREPARING;
            case 2 -> this.status = StoreStatus.RUNNING;
            case 3 -> this.status = StoreStatus.CLOSED;
            case 4 -> this.status = StoreStatus.TEMPORARY_BANDED;
            case 5 -> this.status = StoreStatus.PERMANENT_BANNED;
            default -> throw new IllegalArgumentException("잘못된 입력 코드 store updateStatus() 1~5 까지만 가능");
        }
    }

}