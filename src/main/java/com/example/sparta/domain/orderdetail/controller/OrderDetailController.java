package com.example.sparta.domain.orderdetail.controller;

import com.example.sparta.domain.orderdetail.dto.AddOrderDetailRequestDto;
import com.example.sparta.domain.orderdetail.dto.AddOrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.dto.GetOrderDetailResponseDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PostMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseDto<AddOrderDetailResponseDto>> addOrderDetail(
        @RequestBody AddOrderDetailRequestDto requestDto, @PathVariable Long storeId,
        @PathVariable Long menuId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        AddOrderDetailResponseDto orderDetailResponseDto = orderDetailService.addOrderDetail(
            requestDto, storeId, menuId, userDetails.getUser());

        URI location = URI.create(String.format("/v1/stores/%d", storeId));

        return ResponseEntity.created(location).body(
            ResponseDto.<AddOrderDetailResponseDto>builder().statusCode(HttpStatus.CREATED.value())
                .message("메뉴 담기 완료").data(orderDetailResponseDto).build()
        );
    }
}
