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

    public void deleteOrderDetail(Long orderDetailsId, User user) {

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailsId).orElseThrow(
            () -> new NoSuchElementException("해당 상세 주문이 존재하지 않습니다.")
        );

        if(!orderDetail.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }

        orderDetailRepository.delete(orderDetail);
    }
}
