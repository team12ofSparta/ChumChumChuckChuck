package com.example.sparta.domain.order.dto;

import com.example.sparta.domain.order.entity.Order;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateOrderResponseDto {
    private final Long orderId;
    private final Integer totalPrice;
    private final String requests;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Integer orderStatus;
    private final Long userId;
    private final Long storeId;

    public CreateOrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.totalPrice = order.getTotalPrice();
        this.requests = order.getRequests();
        this.createdAt = order.getCreatedAt();
        this.modifiedAt = order.getModifiedAt();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUser().getUserId();
        this.storeId = order.getStore().getStoreId();
    }
}
