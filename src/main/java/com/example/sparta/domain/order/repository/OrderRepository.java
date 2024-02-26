package com.example.sparta.domain.order.repository;

import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
