package com.example.sparta.domain.review.controller;

import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.entity.Review;
import com.example.sparta.domain.review.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public List<ReviewResponseDto> allReviews(){
        reviewService.findAll();
        return null;
    }

}
