package com.example.sparta.domain.order.service;

import com.example.sparta.domain.order.dto.OrderResponseDto;
import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.order.repository.OrderRepository;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.orderdetail.repository.OrderDetailRepository;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import java.util.ArrayList;
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
    public OrderResponseDto createOrder(String requests, User user) {
        Long totalPrice = 0L;
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByUserAndOrder(user, null);
        List<Long> orderDetailIdList = new ArrayList<>();
        List<String> menuNameList = new ArrayList<>();
        List<Integer> menuQuantityList = new ArrayList<>();
        Store store = orderDetailList.getFirst().getStore();
        for (OrderDetail orderDetail : orderDetailList) {
            totalPrice += orderDetail.getMenu().getPrice() * orderDetail.getQuantity();
            orderDetailIdList.add(orderDetail.getOrderDetailId());
            menuNameList.add(orderDetail.getMenu().getName());
            menuQuantityList.add(orderDetail.getQuantity());
        }
        Order order = new Order(totalPrice, requests, 0, user, store);
        Order savedOrder = orderRepository.save(order);

        for (OrderDetail orderDetail : orderDetailList) { //orderDetail 에 주문 넣어주기
            orderDetail.setOrder(savedOrder);
        }
        return new OrderResponseDto(savedOrder, orderDetailIdList, menuNameList, menuQuantityList);
    }

    public OrderResponseDto getOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문번호입니다."));
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);
        List<Long> orderDetailIdList = new ArrayList<>();
        List<String> menuNameList = new ArrayList<>();
        List<Integer> menuQuantityList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetailIdList.add(orderDetail.getOrderDetailId());
            menuNameList.add(orderDetail.getMenu().getName());
            menuQuantityList.add(orderDetail.getQuantity());
        }
        return new OrderResponseDto(order, orderDetailIdList, menuNameList, menuQuantityList);
    }
}
