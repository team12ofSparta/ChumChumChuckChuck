package com.example.sparta.reviewtest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.review.dto.ReviewResponseDto;
import com.example.sparta.domain.review.entity.Review;
import com.example.sparta.domain.review.repository.ReviewRepository;
import com.example.sparta.domain.review.service.ReviewService;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("전체 리뷰조회")
    public void allReviews(){
        //given
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(4, "안녕하세요", 1L, 1L);
        User user = new User("박정섭","1234","eses565@naver.com","삼천포");
        Store store = new Store();
        Review review = new Review(reviewRequestDto, user, store);

        ReviewRequestDto reviewRequestDto2 = new ReviewRequestDto(4, "안녕하세요", 1L, 1L);
        User user2 = new User("김철수","1234","eses565@naver.com","삼천포");
        Store store2 = new Store();
        Review review2 = new Review(reviewRequestDto, user2, store);

        List<Review> lists = new ArrayList<>();
        lists.add(review);
        lists.add(review2);

       //given(review);
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.save(any())).willReturn(review2);

        given(reviewRepository.findAll()).willReturn(lists);
        //when
        List<Review> all = reviewRepository.findAll();
        Review r = reviewRepository.save(review2);

        //then
        System.out.println(r.getUser().getName());
        Assertions.assertEquals(all.size(), 2);
        Assertions.assertEquals(all.get(0).getUser().getName(),"박정섭");
        Assertions.assertEquals(all.get(1).getUser().getName(),"김철수");
        Assertions.assertEquals(r.getUser().getName(),"김철수");

    }

    @Test
    @DisplayName("단건 리뷰조회")
    public void getReview(){

        //given

        //when

        //then
    }

    @Test
    @DisplayName("리뷰 삭제")
    public void deleteReview(){

        //given

        //when

        //then
    }

    @Test
    @DisplayName("리뷰 수정")
    public void updateReview(){

        //given

        //when

        //then
    }

    @Test
    @DisplayName("리뷰 등록")
    public void createReview(){

        //given

        //when

        //then
    }
}
