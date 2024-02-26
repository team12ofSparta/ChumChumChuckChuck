package com.example.sparta.domain.review.service;

import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.entity.Review;
import com.example.sparta.domain.review.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    //리뷰 전체조회
    public List<ReviewResponseDto> findAll() {
        return reviewRepository.findAll().stream().map(ReviewResponseDto::new).toList();
    }

    //리뷰 단건조회
    //리뷰가 존재하지 않으면 예외처리
    public ReviewResponseDto findOne(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new NullPointerException("리뷰가 존재하지 않습니다."));

        return new ReviewResponseDto(review);
    }

    //리뷰 수정
    //잘못된 값 받을때 예외처리
    //null 갑을 받을때 예외처리
    public ReviewResponseDto updateOne(Long id, ReviewRequestDto reviewRequestDto) {

        if (reviewRequestDto.getRating() == null) {
            throw new IllegalArgumentException("Rating 올바른 값을 넣어주세요.");
        }
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new NullPointerException("수정할 리뷰가 없습니다."));
        review.updateOne(reviewRequestDto.getRating(), reviewRequestDto.getContent());
        Review reviewSave = reviewRepository.save(review);

        return new ReviewResponseDto(reviewSave);
    }

    //리뷰 등록
    //별점이 5이상 넘으면 예외처리
    //null값을 받을때 예외처리
    public ReviewResponseDto register(ReviewRequestDto reviewRequestDto) {
        if (reviewRequestDto.getRating() > 5) {
            throw new IllegalArgumentException("rating 6이상할 수 없습니다.");
        }
        if (reviewRequestDto.getRating() == null) {
            throw new NullPointerException("Rating 올바른 값을 넣어주세요.");
        }
        Review review = new Review(reviewRequestDto);

        return new ReviewResponseDto(reviewRepository.save(review));
    }

    //리뷰 단일 삭제
    //리뷰가 존재하지 않으면 예외처리
    public void deleteOne(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new NullPointerException("존재하지 않는 리뷰입니다"));

        reviewRepository.delete(review);
    }
}
