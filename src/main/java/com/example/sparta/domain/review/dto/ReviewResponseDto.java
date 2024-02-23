package com.example.sparta.domain.review.dto;

import com.example.sparta.domain.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long reviewId;
    private Integer rating;
    private String content;

    public ReviewResponseDto(Review review){
        this.reviewId = review.getReviewId();
        this.rating = review.getRating();
        this.content = review.getContent();
    }
}
