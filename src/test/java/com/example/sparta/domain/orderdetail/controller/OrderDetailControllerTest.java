package com.example.sparta.domain.orderdetail.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyInt;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.orderdetail.dto.GetOrderDetailMenuResponseDto;
import com.example.sparta.domain.orderdetail.dto.GetOrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.dto.OrderDetailRequestDto;
import com.example.sparta.domain.orderdetail.dto.OrderDetailResponseDto;
import com.example.sparta.domain.orderdetail.entity.OrderDetail;
import com.example.sparta.domain.orderdetail.service.OrderDetailService;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.entity.UserRoleEnum;
import com.example.sparta.global.MockSpringSecurityFilter;
import com.example.sparta.global.config.WebSecurityConfig;
import com.example.sparta.global.impl.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {OrderDetailController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
class OrderDetailControllerTest {

    private MockMvc mockMvc;
    private Principal principal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    OrderDetailService orderDetailService;

    User user;
    Store store;
    Menu menu1, menu2;
    OrderDetail orderDetail1, orderDetail2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

        mockSetup();
    }

    private void mockSetup() {
        // Mock 테스트 유저 생성
        Long userId = 100L;
        String username = "abc123";
        String password = "abc12345";
        user = new User(username, password, "abc123@naver.com", "인천", UserRoleEnum.USER, 1L);
        user.setUserId(userId);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        principal = new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());

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

        orderDetail1 = new OrderDetail(1, user, store, null, menu1);
        orderDetail1.setOrderDetailId(1L);
        orderDetail2 = new OrderDetail(3, user, store, null, menu2);
        orderDetail2.setOrderDetailId(2L);
    }

    @Test
    void addOrderDetail() throws Exception {
        // given
        OrderDetailRequestDto requestDto = new OrderDetailRequestDto(1);
        String postInfo = objectMapper.writeValueAsString(requestDto);

        OrderDetailResponseDto responseDto = OrderDetailResponseDto.builder().menuId(1L)
            .menuName("메뉴 1").menuPrice(10000L).quantity(1).build();

        given(orderDetailService.addOrderDetail(any(OrderDetailRequestDto.class), anyLong(),
            anyLong(), any(User.class))).willReturn(responseDto);

        // when
        var action = mockMvc.perform(post("/stores/1/menus/1").content(postInfo).contentType(
            MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).principal(principal));

        // then
        action.andDo(print());
        action.andExpect(status().isCreated());
        verify(orderDetailService, times(1)).addOrderDetail(any(OrderDetailRequestDto.class),
            eq(1L), eq(1L), eq(user));
    }

    @Test
    void getOrderDetail() throws Exception {
        // given
        List<GetOrderDetailMenuResponseDto> responseDtoList =
            List.of(GetOrderDetailMenuResponseDto.builder().menuId(menu1.getMenuId())
                    .menuName(menu1.getName()).menuPrice(menu1.getPrice())
                    .quantity(orderDetail1.getQuantity()).build(),
                GetOrderDetailMenuResponseDto.builder().menuId(menu2.getMenuId())
                    .menuName(menu2.getName()).menuPrice(menu2.getPrice())
                    .quantity(orderDetail2.getQuantity()).build());

        GetOrderDetailResponseDto responseDto = GetOrderDetailResponseDto.builder()
            .storeId(store.getStoreId())
            .storeName(store.getName())
            .orderDetailMenuResponseDtoList(responseDtoList).build();

        given(orderDetailService.getOrderDetail(any(User.class))).willReturn(responseDto);

        // when
        var action = mockMvc.perform(
            get("/orderDetails").accept(MediaType.APPLICATION_JSON).principal(principal));

        // then
        action.andDo(print());
        action.andExpect(status().isOk());
        verify(orderDetailService, times(1)).getOrderDetail(any(User.class));
    }

    @Test
    void deleteOrderDetail() throws Exception {
        // given
        Long deletedId = orderDetail1.getOrderDetailId();

        given(orderDetailService.deleteOrderDetail(anyLong(), any(User.class))).willReturn(
            deletedId);

        // when
        var action = mockMvc.perform(
            delete("/orderDetails/1").accept(MediaType.APPLICATION_JSON).principal(principal));

        // then
        action.andDo(print());
        action.andExpect(status().isNoContent());
        verify(orderDetailService, times(1)).deleteOrderDetail(eq(orderDetail1.getOrderDetailId()),
            any(User.class));
    }

    @Test
    void updateOrderDetail() throws Exception {
        // given
        Integer quantity = 5;

        OrderDetailResponseDto responseDto = OrderDetailResponseDto.builder()
            .menuId(menu1.getMenuId()).menuName(menu1.getName()).menuPrice(menu1.getPrice())
            .quantity(quantity).build();

        given(orderDetailService.updateOrderDetail(anyLong(), anyInt(), any(User.class)))
            .willReturn(responseDto);

        // when
        var action = mockMvc.perform(
            patch("/orderDetails/1").param("quantity", String.valueOf(quantity))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .principal(principal));

        // then
        action.andDo(print());
        action.andExpect(status().isOk());
        verify(orderDetailService, times(1)).updateOrderDetail(eq(orderDetail1.getOrderDetailId()),
            eq(quantity), any(User.class));
    }
}