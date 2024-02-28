package com.example.sparta.domain.orderdetail.controller;

import com.example.sparta.domain.orderdetail.dto.GetOrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.dto.OrderDetailRequestDto;
import com.example.sparta.domain.orderdetail.dto.OrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.service.OrderDetailService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseDto<OrderDetailResponseDto>> addOrderDetail(
        @RequestBody OrderDetailRequestDto requestDto, @PathVariable Long storeId,
        @PathVariable Long menuId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        OrderDetailResponseDto orderDetailResponseDto = orderDetailService.addOrderDetail(
            requestDto, storeId, menuId, userDetails.getUser());

        URI location = URI.create(String.format("/v1/stores/%d", storeId));

        return ResponseEntity.created(location).body(
            ResponseDto.<OrderDetailResponseDto>builder().statusCode(HttpStatus.CREATED.value())
                .data(orderDetailResponseDto).build()
        );
    }

    @GetMapping("/order-details")
    public ResponseEntity<ResponseDto<GetOrderDetailResponseDto>> getOrderDetail(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        GetOrderDetailResponseDto getOrderDetailResponseDto = orderDetailService.getOrderDetail(
            userDetails.getUser());

        return ResponseEntity.ok()
            .body(ResponseDto.<GetOrderDetailResponseDto>builder().statusCode(HttpStatus.OK.value())
                .data(getOrderDetailResponseDto).build());
    }

    @DeleteMapping("/order-details/{orderDetailId}")
    public ResponseEntity<ResponseDto<Void>> deleteOrderDetail(@PathVariable Long orderDetailId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        orderDetailService.deleteOrderDetail(orderDetailId, userDetails.getUser());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/order-details/{orderDetailId}")
    public ResponseEntity<ResponseDto<OrderDetailResponseDto>> updateOrderDetail(
        @PathVariable Long orderDetailId, @RequestParam(name = "quantity") Integer quantity,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        OrderDetailResponseDto responseDto = orderDetailService.updateOrderDetail(orderDetailId,
            quantity, userDetails.getUser());

        return ResponseEntity.ok().body(
            ResponseDto.<OrderDetailResponseDto>builder().statusCode(HttpStatus.OK.value())
                .data(responseDto).build());
    }
}
