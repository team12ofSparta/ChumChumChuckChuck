package com.example.sparta.domain.review.repository;

import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.review.entity.Review;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByOrder(Order order);
}