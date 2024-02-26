package com.example.sparta.domain.orderdetail.repository;

import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByUserAndOrder(User user, Order order);

    @Query("select o from OrderDetail o where o.user.userId = ?1 and o.order is null")
    List<OrderDetail> findAllByUserAndOrderIsNull(Long userId);
}
