package com.example.sparta.domain.order.service;

import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.order.repository.OrderRepository;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.orderdetail.repository.OrderDetailRepository;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional
    public Order createOrder(String requests, User user) {
        int totalPrice = 0;
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByUserAndOrder(user, null);
        Store store = orderDetailList.getFirst().getStore();
        for (OrderDetail orderDetail : orderDetailList) {
            totalPrice += orderDetail.getMenu().getPrice() * orderDetail.getQuantity();
        }
        Order order = new Order(totalPrice, requests, 0, user, store);
        Order savedOrder = orderRepository.save(order);

        for(OrderDetail orderDetail : orderDetailList){ //orderDetail 에 주문 넣어주기
            orderDetail.setOrder(savedOrder);
        }
        return savedOrder;
    }
}
