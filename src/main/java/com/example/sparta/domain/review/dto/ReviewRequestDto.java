package com.example.sparta.domain.review.dto;

import java.time.LocalDateTime;
import java.util.Locale;
import lombok.Getter;
import org.springframework.cglib.core.Local;

@Getter
public class ReviewRequestDto {

    private Integer rating;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAy;

}
