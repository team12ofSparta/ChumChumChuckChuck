package com.example.sparta.reviewtest.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.review.entity.Review;
import com.example.sparta.domain.review.repository.ReviewRepository;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReviewRepositoryTest {

    @Mock
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("Repository 테스트")
    public void RepositoryTest(){
        //given
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(4, "안녕하세요", 1L, 1L);
        User user = new User("박정섭","1234","eses565@naver.com","삼천포");
        Store store = new Store();
        Order order1 = new Order();
        Review review = new Review(reviewRequestDto, user, store, order1);

        ReviewRequestDto reviewRequestDto2 = new ReviewRequestDto(4, "안녕하세요", 1L, 1L);
        User user2 = new User("김철수","1234","eses565@naver.com","삼천포");
        Store store2 = new Store();
        Order order2 = new Order();
        Review review2 = new Review(reviewRequestDto, user2, store2, order2);

        List<Review> lists = new ArrayList<>();
        lists.add(review);
        lists.add(review2);

        //given(save)
        given(reviewRepository.save(any())).willReturn(review);
        given(reviewRepository.save(any())).willReturn(review2);

        //given(findAll)
        given(reviewRepository.findAll()).willReturn(lists);

        //given(update)
        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));

        //when
        List<Review> all = reviewRepository.findAll();
        Review r = reviewRepository.save(review2);
        Optional<Review> byId = reviewRepository.findById(1L);

        //then
        //(find)
        Assertions.assertEquals(byId.get().getContent(),"안녕하세요");
        Assertions.assertEquals(byId.get().getRating(),4);

        //(findAll)
        Assertions.assertEquals(all.size(), 2);

        //(save)
        Assertions.assertEquals(r.getUser().getName(),"김철수");
    }

}
