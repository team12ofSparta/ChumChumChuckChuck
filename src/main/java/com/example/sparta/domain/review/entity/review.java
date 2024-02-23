package com.example.sparta.domain.review.entity;

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
import java.awt.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reviews")
public class review extends Timestamped {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer rating;
    @Column
    private String content;

//    @JoinColumn
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;
//
//    @JoinColumn
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Store store;
//
//    @JoinColumn
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Menu menu;

}
