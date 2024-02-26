package com.example.sparta.domain.review.entity;

import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.review.dto.ReviewRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "store_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @JoinColumn(name = "menu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    //생성자
    public Review(ReviewRequestDto reviewRequestDto) {
        this.rating = reviewRequestDto.getRating();
        this.content = reviewRequestDto.getContent();
    }

    public void updateOne(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }
}