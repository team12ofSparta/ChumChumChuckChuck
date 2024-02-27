package com.example.sparta.domain.review.controller;

import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.entity.Review;
import com.example.sparta.domain.review.service.ReviewService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.text.html.parser.Entity;
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
            .message("리뷰 전체 조회 완료")
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
            .message("리뷰 선택 조회 완료")
            .statusCode(HttpStatus.OK.value())
            .data(reviewResponseDto)
            .build());
    }

    //리뷰수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<ResponseDto<ReviewResponseDto>> updateReview(@PathVariable Long reviewId,
        @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.updateOne(reviewId, reviewRequestDto);

        return ResponseEntity.ok()
            .body(ResponseDto.
                <ReviewResponseDto>builder()
                .message("리뷰 수정 완료")
                .statusCode(HttpStatus.OK.value())
                .data(reviewResponseDto)
                .build());
    }

    //리뷰 등록
    @PostMapping
    public ResponseEntity<ResponseDto<ReviewResponseDto>> registerReview(
        @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto register = reviewService.register(reviewRequestDto);

        return ResponseEntity.ok()
            .body(ResponseDto.
                <ReviewResponseDto>builder()
                .message("리뷰 등록 완료")
                .statusCode(HttpStatus.OK.value())
                .data(register)
                .build());
    }

    //리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ResponseDto<ReviewResponseDto>> deleteReview(
        @PathVariable Long reviewId) {
        reviewService.deleteOne(reviewId);

        return ResponseEntity.ok()
            .body(ResponseDto.
                <ReviewResponseDto>builder()
                .message("리뷰 삭제 완료")
                .statusCode(HttpStatus.OK.value())
                .data(null)
                .build());
    }
}
