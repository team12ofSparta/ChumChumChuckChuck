package com.example.sparta.domain.orderdetail.repository;

import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByUserAndOrder(User user, Order order);

    List<OrderDetail> findAllByUser(User user);

    List<OrderDetail> findAllByOrder(Order order);
}
