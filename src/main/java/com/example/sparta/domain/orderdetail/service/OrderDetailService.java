package com.example.sparta.domain.orderdetail.service;

import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.menu.repository.MenuRepository;
import com.example.sparta.domain.orderdetail.dto.AddOrderDetailRequestDto;
import com.example.sparta.domain.orderdetail.dto.AddOrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.dto.GetOrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.orderdetail.repository.OrderDetailRepository;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    public AddOrderDetailResponseDto addOrderDetail(AddOrderDetailRequestDto requestDto,
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

        OrderDetail orderDetail = new OrderDetail(requestDto.getQuantity(), user, store, null,
            menu);

        orderDetailRepository.save(orderDetail);

        return AddOrderDetailResponseDto.builder().menuId(menuId).menuName(menu.getName())
            .menuPrice(menu.getPrice()).quantity(
                requestDto.getQuantity()).build();
    }
}
