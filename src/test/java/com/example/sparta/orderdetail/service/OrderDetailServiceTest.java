package com.example.sparta.orderdetail.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.menu.repository.MenuRepository;
import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.orderdetail.dto.GetOrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.dto.OrderDetailRequestDto;
import com.example.sparta.domain.orderdetail.dto.OrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.orderdetail.repository.OrderDetailRepository;
import com.example.sparta.domain.orderdetail.service.OrderDetailService;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {

    @Mock
    OrderDetailRepository orderDetailRepository;
    @Mock
    StoreRepository storeRepository;
    @Mock
    MenuRepository menuRepository;

    OrderDetailService orderDetailService;

    User user;
    Store store;
    Menu menu1, menu2;
    Order order;

    @BeforeEach
    void setUp() {
        orderDetailService = new OrderDetailService(orderDetailRepository, storeRepository, menuRepository);

        user = new User("user123", "password123", "user@user.com", "서울");
        user.setUserId(1L);

        StoreRequestDto storeRequestDto = new StoreRequestDto();
        storeRequestDto.setAddress("서울");
        storeRequestDto.setName("가게");
        storeRequestDto.setCategory("한식");
        storeRequestDto.setContent("가게임");
        storeRequestDto.setRating(0f);
        storeRequestDto.setRating(0f);
        storeRequestDto.setDeliveryAddress("서울");
        storeRequestDto.setDibsCount(0);
        storeRequestDto.setReviewCount(0);

        store = new Store(storeRequestDto, user);

        menu1 = new Menu("메뉴 1", 10000L, store);
        menu1.setMenuId(1L);
        menu2 = new Menu("메뉴 2", 5000L, store);
        menu2.setMenuId(2L);
    }

    @Test
    void addOrderDetail() {
        // given
        given(storeRepository.findById(1L)).willReturn(Optional.of(store));
        given(menuRepository.findById(1L)).willReturn(Optional.of(menu1));

        OrderDetail orderDetail = new OrderDetail(1, user, store, null, menu1);

        // when
        OrderDetailResponseDto responseDto = orderDetailService.addOrderDetail(new OrderDetailRequestDto(1), 1L, 1L, user);

        // then
        assertNotNull(responseDto);
        assertEquals(orderDetail.getQuantity(), responseDto.getQuantity());
    }

    @Test
    void getOrderDetail() {
        // given
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail1 = new OrderDetail(1, user, store, null, menu1);
        OrderDetail orderDetail2 = new OrderDetail(2, user, store, null, menu2);
        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);
        given(orderDetailRepository.findAllByUser(user)).willReturn(orderDetailList);

        // when
        GetOrderDetailResponseDto responseDto = orderDetailService.getOrderDetail(user);

        // then
        assertNotNull(responseDto);
        assertEquals(orderDetailList.get(0).getStore().getName(), responseDto.getStoreName());
        assertEquals(orderDetailList.get(0).getMenu().getName(), responseDto.getMenuNameList().get(0));
    }

    @Test
    void deleteOrderDetail() {
        // given
        OrderDetail orderDetail = new OrderDetail(1, user, store, null, menu1);
        orderDetail.setOrderDetailId(1L);

        given(orderDetailRepository.findById(1L)).willReturn(Optional.of(orderDetail));

        // when
        Long deleteOrderDetailId = orderDetailService.deleteOrderDetail(1L, user);

        // then
        assertEquals(orderDetail.getOrderDetailId(), deleteOrderDetailId);
    }

    @Test
    void updateOrderDetail() {
        // given
        OrderDetail orderDetail = new OrderDetail(1, user, store, null, menu1);
        orderDetail.setOrderDetailId(1L);

        given(orderDetailRepository.findById(1L)).willReturn(Optional.of(orderDetail));

        Integer quantity = 5;

        // when
        OrderDetailResponseDto responseDto = orderDetailService.updateOrderDetail(1L, quantity, user);

        // then
        assertEquals(quantity, responseDto.getQuantity());
    }
}
