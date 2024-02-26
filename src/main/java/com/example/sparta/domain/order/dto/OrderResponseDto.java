package com.example.sparta.domain.order.dto;

import com.example.sparta.domain.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderResponseDto {

    private final Long orderId;
    private final List<Long> orderDetailIdList;
    private final List<String> menuNameList;
    private final List<Integer> menuQuantityList;
    private final Long totalPrice;
    private final String requests;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final Integer orderStatus;
    private final Long userId;
    private final Long storeId;

    public OrderResponseDto(Order order, List<Long> orderDetailIdList, List<String> menuNameList,
        List<Integer> menuQuantityList) {
        this.orderId = order.getOrderId();

        this.orderDetailIdList = orderDetailIdList;
        this.menuNameList = menuNameList;
        this.menuQuantityList = menuQuantityList;

        this.totalPrice = order.getTotalPrice();
        this.requests = order.getRequests();
        this.createdAt = order.getCreatedAt();
        this.modifiedAt = order.getModifiedAt();
        this.orderStatus = order.getOrderStatus();
        this.userId = order.getUser().getUserId();
        this.storeId = order.getStore().getStoreId();
    }
}
