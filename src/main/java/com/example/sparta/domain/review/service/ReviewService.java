package com.example.sparta.domain.review.service;

import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.entity.Review;
import com.example.sparta.domain.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    //리뷰 전체조회
    public List<ReviewResponseDto> findAll(){
       return reviewRepository.findAll().stream().map(ReviewResponseDto::new).toList();
    }

    //리뷰 단건조회
    public ReviewResponseDto findOne(Long id) {
        Review review = reviewRepository.findById(id).get();
       return new ReviewResponseDto(review);
    }

    //리뷰 수정
    public ReviewResponseDto updateOne(Long id, ReviewRequestDto reviewRequestDto) { //todo 리펙토링 필요
        Review review = reviewRepository.findById(id).get();
        review.setRating(reviewRequestDto.getRating());
        review.setContent(reviewRequestDto.getContent());
        Review reviewSave = reviewRepository.save(review);

        return new ReviewResponseDto(reviewSave);
    }

    //리뷰 등록
    public ReviewResponseDto register(ReviewRequestDto reviewRequestDto){
        Review review = reviewRepository.save(new Review(reviewRequestDto));
        return new ReviewResponseDto(review);
    }

    //리뷰 단일 삭제
    public void deleteOne(Long id){ //todo 삭제된 상태 메세지 출력하기
        Review review = reviewRepository.findById(id).get();
        reviewRepository.delete(review);
    }
}
