package com.example.sparta.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {

    private Integer rating;
    private String content;
    private Long menuId;
    private Long storeId;
}