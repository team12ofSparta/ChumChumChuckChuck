package com.example.sparta.domain.order.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.example.sparta.domain.order.dto.CreateOrderRequestDto;
import com.example.sparta.domain.order.dto.OrderResponseDto;
import com.example.sparta.domain.order.entity.Order;
import com.example.sparta.domain.order.service.OrderService;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.global.impl.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;
    User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);

        UserDetailsImpl mockUserDetails = new UserDetailsImpl(testUser);

        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(mockUserDetails, null, null)
        );

        mockMvc = webAppContextSetup(context).build();
    }


    @Nested
    @DisplayName("주문 생성")
    @WithMockUser // Filter 통과 가정
    class createOrder{
        @DisplayName("주문 생성 성공")
        @Test
        void createOrder_success() throws Exception {
            //given
            Order order = new Order();
            Store store = new Store();

            order.setUser(testUser);
            order.setStore(store);

            CreateOrderRequestDto requestDto = new CreateOrderRequestDto("test request");

            OrderResponseDto orderResponseDto = new OrderResponseDto(
                order, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            );
            given(orderService.createOrder(any(CreateOrderRequestDto.class), any(User.class))).willReturn(orderResponseDto);

            //when
            ResultActions action = mockMvc.perform(
                post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
            );
            //then
            action.andDo(print()).andExpect(status().isCreated());
        }
        @DisplayName("주문 생성 실패")
        @Test
        void createOrder_fail() throws Exception {
            //given
            Order order = new Order();
            Store store = new Store();

            order.setUser(testUser);
            order.setStore(store);

            CreateOrderRequestDto requestDto = new CreateOrderRequestDto("test request");
            given(orderService.createOrder(any(CreateOrderRequestDto.class), any(User.class))).willThrow(new IllegalArgumentException());

            //when
            ResultActions action = mockMvc.perform(
                post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
            );
            //then
            action.andDo(print()).andExpect(status().isBadRequest());
        }
    }

}