package com.example.sparta.domain.order.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.order.repository.OrderRepository;
import com.example.sparta.domain.orderdetail.repository.OrderDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderDetailRepository orderDetailRepository;

}