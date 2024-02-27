package com.example.sparta.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.Getter;
import org.springframework.cglib.core.Local;

@Getter
public class ReviewRequestDto {

    private Integer rating;
    private String content;
    private Long menuId;
    private Long storeId;
    private Long userId;
}