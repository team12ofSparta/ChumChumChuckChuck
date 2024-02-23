package com.example.sparta.domain.review.dto;

import com.example.sparta.domain.review.entity.Review;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewResponseDto {

    private Long reviewId;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getReviewId();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.modifiedAt = review.getModifiedAt();
    }
}
