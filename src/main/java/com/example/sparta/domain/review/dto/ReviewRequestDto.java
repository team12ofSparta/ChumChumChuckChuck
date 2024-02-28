package com.example.sparta.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {

    private Integer rating;
    private String content;
    private Long orderId;
    private Long storeId;
}