package com.example.sparta.domain.orderdetail.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.menu.repository.MenuRepository;
import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.order.repository.OrderRepository;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.entity.UserRoleEnum;
import com.example.sparta.domain.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    OrderRepository orderRepository;

    User user;
    Store store;
    Menu menu1, menu2, menu3;
    Order order;
    OrderDetail orderDetail1, orderDetail2, orderDetail3;

    @BeforeEach
    void setUp() {
        user = new User("user123", "password123", "user@naver.com", "인천", UserRoleEnum.USER, 1L);
        user = userRepository.save(user);

        StoreRequestDto storeRequestDto = new StoreRequestDto();
        storeRequestDto.setName("가게 1");
        storeRequestDto.setCategory("한식");
        storeRequestDto.setAddress("인천");
        storeRequestDto.setContent("가게 1 입니다");
        storeRequestDto.setRating(0f);
        storeRequestDto.setDibsCount(0);
        storeRequestDto.setDeliveryAddress("인천");
        storeRequestDto.setReviewCount(0);

        store = new Store(storeRequestDto, user);
        storeRepository.save(store);

        menu1 = new Menu("메뉴 1", 10000L, store);
        menu2 = new Menu("메뉴 2", 15000L, store);
        menu3 = new Menu("메뉴 3", 5000L, store);
        menuRepository.saveAll(List.of(menu1, menu2, menu3));

        orderDetail1 = orderDetailRepository.save(new OrderDetail(1, user, store, null, menu1));
        orderDetail2 = orderDetailRepository.save(new OrderDetail(2, user, store, null, menu2));
        orderDetail3 = orderDetailRepository.save(new OrderDetail(3, user, store, null, menu3));

        order = new Order(30000L, "요청 사항", 1, user, store);
        orderRepository.save(order);
    }

    @Test
    void findAllByUserAndOrder() {
        // given

        // when
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByUserAndOrder(user, null);

        // then
        assertNotNull(orderDetailList);
        assertTrue(orderDetailList.containsAll(List.of(orderDetail1, orderDetail2, orderDetail3)));
    }

    @Test
    void findAllByUserAndOrderNull() {
        // given
        User findUser = userRepository.findByEmail("user@naver.com").orElse(null);

        // when
        assert findUser != null;
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByUserAndOrderIsNull(
            findUser.getUserId());

        // then
        assertNotNull(orderDetailList);
        assertTrue(orderDetailList.containsAll(List.of(orderDetail1, orderDetail2, orderDetail3)));
    }

    @Test
    void findAllByUser() {
        // given

        // when
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByUser(user);

        // then
        assertNotNull(orderDetailList);
        assertTrue(orderDetailList.containsAll(List.of(orderDetail1, orderDetail2, orderDetail3)));
    }

    @Test
    void findAllByOrder() {
        // given
        orderDetail1.setOrder(order);
        orderDetail2.setOrder(order);
        orderDetail3.setOrder(order);

        // when
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrder(order);

        // then
        assertNotNull(orderDetailList);
        assertTrue(orderDetailList.containsAll(List.of(orderDetail1, orderDetail2, orderDetail3)));
    }
}
