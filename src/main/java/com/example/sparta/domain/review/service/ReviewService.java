package com.example.sparta.domain.review.service;

import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.entity.Review;
import com.example.sparta.domain.review.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<ReviewResponseDto> findAll(){
        reviewRepository.findAll();//todo ReviewresponseDto의 리스트를 만들어서 반환하기
        return null;//임시 방편
    }

    public ReviewResponseDto findOne(Long id) {
        Review review = reviewRepository.findById(id).get();
       return new ReviewResponseDto(review);
    }
}
