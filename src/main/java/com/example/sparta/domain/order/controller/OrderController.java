package com.example.sparta.domain.order.controller;

import com.example.sparta.domain.order.dto.CreateOrderRequestDto;
import com.example.sparta.domain.order.dto.ModifyOrderRequestDto;
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
import org.springframework.web.bind.annotation.PatchMapping;
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
        OrderResponseDto orderResponseDto;
        try {
            orderResponseDto = orderService.createOrder(requestDto,
                userDetails.getUser());
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(ResponseDto.<OrderResponseDto>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseDto.<OrderResponseDto>builder()
                .statusCode(HttpStatus.CREATED.value())
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
                    .data(null)
                    .build()
                );
        }
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.<OrderResponseDto>builder()
            .statusCode(HttpStatus.OK.value())
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
                .data(null)
                .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<List<OrderResponseDto>>builder()
            .statusCode(HttpStatus.OK.value())
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
                .data(null)
                .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<Void>builder()
            .statusCode(HttpStatus.OK.value())
            .data(null)
            .build()
        );
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> modifyOrderRequest(
        @AuthenticationPrincipal UserDetailsImpl userDetails
        , @PathVariable Long orderId
        , @RequestBody ModifyOrderRequestDto modifyOrderRequestDto) {
        OrderResponseDto orderResponseDto;
        try {
            orderResponseDto = orderService.modifyOrderRequest(userDetails.getUser(),
                orderId, modifyOrderRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(ResponseDto.<OrderResponseDto>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .data(null)
                .build());
        }
        return ResponseEntity.ok().body(ResponseDto.<OrderResponseDto>builder()
            .statusCode(HttpStatus.OK.value())
            .data(orderResponseDto)
            .build()
        );
    }
}
