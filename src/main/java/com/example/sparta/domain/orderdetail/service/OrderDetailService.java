package com.example.sparta.domain.orderdetail.service;

import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.menu.repository.MenuRepository;
import com.example.sparta.domain.orderdetail.dto.OrderDetailRequestDto;
import com.example.sparta.domain.orderdetail.dto.OrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.dto.GetOrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.orderdetail.repository.OrderDetailRepository;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public OrderDetailResponseDto addOrderDetail(OrderDetailRequestDto requestDto,
        Long storeId, Long menuId, User user) {
        if (requestDto.getQuantity() < 1) {
            throw new IllegalArgumentException("주문 항목이 없습니다.");
        }

        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new NoSuchElementException("해당 가게를 찾을 수 없습니다.")
        );

        Menu menu = menuRepository.findById(menuId).orElseThrow(
            () -> new NoSuchElementException("해당 메뉴를 찾을 수 없습니다.")
        );

        validateQuantity(requestDto.getQuantity());

        OrderDetail orderDetail = new OrderDetail(requestDto.getQuantity(), user, store, null,
            menu);

        orderDetailRepository.save(orderDetail);

        return OrderDetailResponseDto.builder().menuId(menuId).menuName(menu.getName())
            .menuPrice(menu.getPrice()).quantity(
                requestDto.getQuantity()).build();
    }

    public GetOrderDetailResponseDto getOrderDetail(User user) {

        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByUser(user);

        if (orderDetailList == null || orderDetailList.isEmpty()) {
            return null;
        }

        Long storeId = orderDetailList.get(0).getStore().getStoreId();
        String storeName = orderDetailList.get(0).getStore().getName();

        List<Long> menuIdList = new ArrayList<>();
        List<String> menuNameList = new ArrayList<>();
        List<Long> menuPriceList = new ArrayList<>();
        List<Integer> quantityList = new ArrayList<>();

        for (OrderDetail od : orderDetailList) {
            menuIdList.add(od.getMenu().getMenuId());
            menuNameList.add(od.getMenu().getName());
            menuPriceList.add(od.getMenu().getPrice());
            quantityList.add(od.getQuantity());
        }

        return GetOrderDetailResponseDto.builder().storeId(storeId).storeName(storeName)
            .menuIdList(menuIdList).menuNameList(menuNameList)
            .menuPriceList(menuPriceList).quantityList(quantityList).build();
    }

    @Transactional
    public Long deleteOrderDetail(Long orderDetailId, User user) {

        OrderDetail orderDetail = validateOrderDetail(orderDetailId, user);

        orderDetailRepository.delete(orderDetail);

        return orderDetailId;
    }

    @Transactional
    public OrderDetailResponseDto updateOrderDetail(Long orderDetailId, Integer quantity,
        User user) {

        OrderDetail orderDetail = validateOrderDetail(orderDetailId, user);

        validateQuantity(quantity);

        orderDetail.setQuantity(quantity);

        return OrderDetailResponseDto.builder().menuId(orderDetail.getMenu().getMenuId())
            .menuName(orderDetail.getMenu().getName()).menuPrice(orderDetail.getMenu().getPrice())
            .quantity(orderDetail.getQuantity()).build();
    }

    private OrderDetail validateOrderDetail(Long orderDetailId, User user) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(
            () -> new NoSuchElementException("해당 상세 주문이 존재하지 않습니다.")
        );

        if (!orderDetail.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }

        return orderDetail;
    }

    private void validateQuantity(Integer quantity) {
        if (quantity < 0 || quantity > 999) {
            throw new IllegalArgumentException("올바르지 않은 수량 입력입니다.");
        }
    }
}
