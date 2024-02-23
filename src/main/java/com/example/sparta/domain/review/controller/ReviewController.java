package com.example.sparta.domain.review.controller;

import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    //모든 리뷰조회
    @GetMapping("/reviews")
    public List<ReviewResponseDto> allReviews(){
        return reviewService.findAll();
    }

    //단건 리뷰조회
    @GetMapping("/reviews/{reviewId}")
    public ReviewResponseDto OneReview(@PathVariable Long reviewId){
        return reviewService.findOne(reviewId);
    }

    //리뷰수정
    @PutMapping("/reviews/{reviewId}")
    public ReviewResponseDto updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto){
        return reviewService.updateOne(reviewId, reviewRequestDto);
    }

    //리뷰 등록
    @PostMapping("/reviews")
    public ReviewResponseDto registerReview(@RequestBody ReviewRequestDto reviewRequestDto){
        return reviewService.register(reviewRequestDto);
    }

    //리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId){
        reviewService.deleteOne(reviewId);
    }
}
