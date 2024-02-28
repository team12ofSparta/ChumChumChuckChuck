package com.example.sparta.domain.order.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.example.sparta.domain.order.dto.CreateOrderRequestDto;
import com.example.sparta.domain.order.dto.ModifyOrderRequestDto;
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

    @Nested
    @DisplayName("주문 삭제")
    @WithMockUser
    class deleteOrder{
        @Test
        @DisplayName("주문 삭제 - 성공")
        void deleteOrder_success() throws Exception{
            //given
            Order order = new Order();
            Store store = new Store();

            order.setUser(testUser);
            order.setStore(store);

            Long orderId = 1L;
            given(orderService.deleteOrder(any(User.class), any(Long.class))).willReturn(orderId);
            //when
            ResultActions action = mockMvc.perform(
                delete("/orders/1")
                    .accept(MediaType.APPLICATION_JSON)
            );
            //then
            action.andDo(print()).andExpect(status().isOk());
        }
        @Test
        @DisplayName("주문 삭제 - 실패")
        void deleteOrder_fail() throws Exception{
            //given
            Order order = new Order();
            Store store = new Store();

            order.setUser(testUser);
            order.setStore(store);

            given(orderService.deleteOrder(any(User.class), any(Long.class))).willThrow(new IllegalArgumentException());
            //when
            ResultActions action = mockMvc.perform(
                delete("/orders/1")
                    .accept(MediaType.APPLICATION_JSON)
            );
            //then
            action.andDo(print()).andExpect(status().isBadRequest());
        }
    }
    @Nested
    @DisplayName("주문 요청사항 수정")
    @WithMockUser
    class modifyOrderRequests{
        @Test
        @DisplayName("-성공")
        void modifyOrderRequest_success() throws Exception{
            //given
            Order order = new Order();
            Store store = new Store();

            order.setUser(testUser);
            order.setStore(store);
            OrderResponseDto orderResponseDto = new OrderResponseDto(
                order, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
            );

            given(orderService.modifyOrderRequest(any(User.class), any(Long.class),any(ModifyOrderRequestDto.class))).willReturn(orderResponseDto);
            ModifyOrderRequestDto requestDto = new ModifyOrderRequestDto("modify requests.");
            //when
            ResultActions action = mockMvc.perform(
                patch("/orders/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
            );

            //then
            action.andDo(print()).andExpect(status().isOk());
        }
        @Test
        @DisplayName("-실패")
        void modifyOrderRequest_fail() throws Exception{
            //given
            Order order = new Order();
            Store store = new Store();

            order.setUser(testUser);
            order.setStore(store);

            given(orderService.modifyOrderRequest(any(User.class), any(Long.class),any(ModifyOrderRequestDto.class))).willThrow(new IllegalArgumentException());
            ModifyOrderRequestDto requestDto = new ModifyOrderRequestDto("modify requests.");
            //when
            ResultActions action = mockMvc.perform(
                patch("/orders/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
            );

            //then
            action.andDo(print()).andExpect(status().isBadRequest());
        }
    }
    @Nested
    @DisplayName("주문 조회")
    @WithMockUser
    class getOrder{
        @Nested
        @DisplayName("단건 조회")
        class singleOrder{
            @Test
            @DisplayName("- 성공")
            void getSingleOrder_success() throws Exception{
                //given
                Order order = new Order();
                Store store = new Store();

                order.setUser(testUser);
                order.setStore(store);
                OrderResponseDto orderResponseDto = new OrderResponseDto(
                    order, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
                );
                given(orderService.getOrder(any(User.class), any(Long.class))).willReturn(orderResponseDto);

                //when
                ResultActions action = mockMvc.perform(
                    get("/orders/1")
                );

                //then
                action.andDo(print()).andExpect(status().isOk());
            }
            @Test
            @DisplayName("- 실패")
            void getSingleOrder_fail() throws Exception{
                //given
                Order order = new Order();
                Store store = new Store();

                order.setUser(testUser);
                order.setStore(store);

                given(orderService.getOrder(any(User.class), any(Long.class))).willThrow(new IllegalArgumentException());

                //when
                ResultActions action = mockMvc.perform(
                    get("/orders/1")
                );

                //then
                action.andDo(print()).andExpect(status().isBadRequest());
            }
        }
//        @Nested
//        @DisplayName("목록 조회")
//        class listOfOrders{
//            @Test
//            @DisplayName("- 성공")
//            void getListOfOrder_success(){
//
//            }
//        }
    }
}