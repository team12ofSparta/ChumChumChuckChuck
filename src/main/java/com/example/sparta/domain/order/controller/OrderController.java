package com.example.sparta.domain.order.controller;

import com.example.sparta.domain.order.dto.CreateOrderRequestDto;
import com.example.sparta.domain.order.dto.OrderResponseDto;
import com.example.sparta.domain.order.service.OrderService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<ResponseDto<OrderResponseDto>> createOrder(
        @RequestBody CreateOrderRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderResponseDto orderResponseDto = orderService.createOrder(requestDto.getRequests(),
            userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.<OrderResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("주문생성 완료")
                .data(orderResponseDto)
                .build());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long orderId) {
        OrderResponseDto orderResponseDto;
        try {
            orderResponseDto = orderService.getOrder(userDetails.getUser(), orderId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.<OrderResponseDto>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .data(null)
                    .build()
                );
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<OrderResponseDto>builder()
            .statusCode(HttpStatus.OK.value())
            .message("주문 ID를 통한 주문 조회 성공")
            .data(orderResponseDto)
            .build()
        );
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<OrderResponseDto>>> getOrderList(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<OrderResponseDto> orderResponseDtoList;
        try {
            orderResponseDtoList = orderService.getOrderList(userDetails.getUser());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ResponseDto.<List<OrderResponseDto>>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .data(null)
                .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<List<OrderResponseDto>>builder()
            .statusCode(HttpStatus.OK.value())
            .message("주문 목록 조회 성공")
            .data(orderResponseDtoList)
            .build()
        );
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ResponseDto<Void>> deleteOrder(
        @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long orderId) {
        try {
            orderService.deleteOrder(userDetails.getUser(), orderId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseDto.<Void>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .data(null)
                .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<Void>builder()
            .statusCode(HttpStatus.OK.value())
            .message("주문 삭제 성공")
            .data(null)
            .build()
        );
    }
}
