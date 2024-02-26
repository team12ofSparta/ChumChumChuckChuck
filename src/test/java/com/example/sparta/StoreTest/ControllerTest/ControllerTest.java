package com.example.sparta.StoreTest.ControllerTest;


import com.example.sparta.domain.store.controller.StoreController;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.service.StoreService;
import com.example.sparta.domain.user.entity.User;

import com.example.sparta.global.config.WebSecurityConfig;

import com.example.sparta.global.impl.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {StoreController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
public class ControllerTest {
    public MockMvc mvc;
    public Principal mockPrincipal;
    @MockBean
    StoreService Service;
    @Autowired
    public WebApplicationContext context;
    @Autowired
    public ObjectMapper objectMapper;
    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }
    private void mockUserSetup() {
        // Mock 테스트 유져 생성
        User testUser = new User("user", "user", "user@user.com","여기 : 삽니다");

        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
    }
    //
    @Test
    @DisplayName("Store post")
    void test1() throws Exception {
        //given
        this.mockUserSetup();
        StoreRequestDto requestDto= new StoreRequestDto();
        requestDto.setName("store name");
        requestDto.setCategory("store category");
        requestDto.setAddress("store address");
        requestDto.setContent("store content");
        requestDto.setRating(4.4f);
        requestDto.setDibsCount(4);
        requestDto.setReviewCount(4);
        requestDto.setDeliveryAddress("delivery address");

        String postform  = objectMapper.writeValueAsString(requestDto);
        // when - then
        this.mvc.perform(post("/stores")
                        .content(postform)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().is(201))
                .andDo(print());
    }

    @Test
    @DisplayName("Getting StoreList")
    void test2() throws Exception {
        //given
        this.mockUserSetup();

        // when - then
        this.mvc.perform(get("/stores")
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Editing Store")
    void test3() throws Exception {
        //given
        this.mockUserSetup();
        StoreRequestDto requestDto= new StoreRequestDto();
        requestDto.setName("new name");
        requestDto.setCategory("new catagory");
        requestDto.setAddress("new address");
        requestDto.setContent("new content");
        requestDto.setRating(8.4f);
        requestDto.setDibsCount(8);
        requestDto.setReviewCount(8);
        requestDto.setDeliveryAddress("new delivery address");
        String putform  = objectMapper.writeValueAsString(requestDto);
        // when - then
        this.mvc.perform(put("/stores/1")
                        .content(putform)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().is(202))
                .andDo(print());
    }
    @Test
    @DisplayName("delete Store")
    void test4() throws Exception {
        //given
        this.mockUserSetup();
        // when - then
        this.mvc.perform(delete("/stores/1")
                )
                .andExpect(status().is(202))
                .andDo(print());
    }

    @Test
    @DisplayName("get Store by id")
    void test5() throws Exception {
        //given
        this.mockUserSetup();
        // when - then
        this.mvc.perform(get("/stores/1")
                )
                .andExpect(status().is(200))
                .andDo(print());
    }
    @Test
    @DisplayName("get Store by name")
    void test6() throws Exception {
        //given
        this.mockUserSetup();
        // when - then
        this.mvc.perform(get("/stores?name=sehun")
                )
                .andExpect(status().is(200))
                .andDo(print());
    }
}
