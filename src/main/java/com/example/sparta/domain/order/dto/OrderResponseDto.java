package com.example.sparta.domain.order.dto;

import com.example.sparta.domain.order.entity.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderResponseDto {
    private final Long orderId;
    private final Long userId;
    private final Long storeId;
    private final Long totalPrice;
    private final String requests;
    private final Integer orderStatus;
    private final List<OrderDetailResponseBucket> responseBucketList;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public OrderResponseDto(Order order, List<OrderDetailResponseBucket> responseBucketList) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getUserId();
        this.storeId = order.getStore().getStoreId();
        this.totalPrice = order.getTotalPrice();
        this.requests = order.getRequests();
        this.orderStatus = order.getOrderStatus();
        this.responseBucketList = responseBucketList;
        this.createdAt = order.getCreatedAt();
        this.modifiedAt = order.getModifiedAt();
    }
}
