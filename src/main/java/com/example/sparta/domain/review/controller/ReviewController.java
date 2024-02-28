package com.example.sparta.domain.review.controller;

import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.service.ReviewService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    //모든 리뷰조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<ReviewResponseDto>>> allReviews() {
        List<ReviewResponseDto> all = reviewService.findAll();

        return ResponseEntity.ok().body(ResponseDto.
            <List<ReviewResponseDto>>builder()
            .statusCode(HttpStatus.OK.value())
            .data(all)
            .build());
    }

    //단건 리뷰조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ResponseDto<ReviewResponseDto>> oneReview(@PathVariable Long reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.findOne(reviewId);

        return ResponseEntity.ok().body(ResponseDto.
            <ReviewResponseDto>builder()
            .statusCode(HttpStatus.OK.value())
            .data(reviewResponseDto)
            .build());
    }

    //리뷰수정
    //유저검증하기 위해 userDetails에 유저를 반환하여 검증
    @PutMapping("/{reviewId}")
    public ResponseEntity<ResponseDto<ReviewResponseDto>> updateReview(@PathVariable Long reviewId,
        @RequestBody ReviewRequestDto reviewRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ReviewResponseDto reviewResponseDto = reviewService.updateOne(reviewId, reviewRequestDto,
            userDetails.getUser());

        return ResponseEntity.ok()
            .body(ResponseDto.
                <ReviewResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .data(reviewResponseDto)
                .build());
    }

    //리뷰 등록
    @PostMapping
    public ResponseEntity<ResponseDto<ReviewResponseDto>> registerReview(
        @RequestBody ReviewRequestDto reviewRequestDto, UserDetailsImpl userDetails) {

        ReviewResponseDto register = reviewService.register(reviewRequestDto,
            userDetails.getUser());

        return ResponseEntity.ok()
            .body(ResponseDto.
                <ReviewResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .data(register)
                .build());
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ResponseDto<ReviewResponseDto>> deleteReview(
        @PathVariable Long reviewId, UserDetailsImpl userDetails) {

        reviewService.deleteOne(reviewId, userDetails.getUser());

        return ResponseEntity.ok()
            .body(ResponseDto.
                <ReviewResponseDto>builder()
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build());
    }
}
