package com.example.sparta.domain.order.controller;

import com.example.sparta.domain.order.dto.CreateOrderRequestDto;
import com.example.sparta.domain.order.dto.CreateOrderResponseDto;
import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.order.service.OrderService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateOrderResponseDto>> createOrder(@RequestBody CreateOrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Order order = orderService.createOrder(requestDto.getRequests(), userDetails.getUser());
        CreateOrderResponseDto createOrderResponseDto = new CreateOrderResponseDto(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.<CreateOrderResponseDto>builder()
            .statusCode(HttpStatus.CREATED.value())
            .message("주문생성 완료")
            .data(createOrderResponseDto)
            .build());
    }
}
