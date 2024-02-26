package com.example.sparta.StoreTest.ServiceTest;

import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.dto.StoreResponseDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.store.service.StoreService;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.repository.UserRepository;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    @Mock
    StoreRepository MockStoreRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("스토어 생성")
    void test1(){
        //given

        //스토어 request
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setName("service name");
        requestDto.setCategory("Chicken");
        requestDto.setDibsCount(100);
        requestDto.setRating(5.5f);
        requestDto.setAddress("service test land");
        requestDto.setDeliveryAddress("delivers everywhere darling");
        requestDto.setReviewCount(34);
        requestDto.setContent("content");

        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.save(any())).willReturn(new Store());

        //then
        StoreResponseDto storeResponseDto = storeService.createStore(requestDto,user);

        assertEquals(user.getUserId(),storeResponseDto.getStoreId());
    }
    //given(userRepository.findById(1L)).willReturn(Optional.of(user));
    @Test
    @DisplayName("스토어 리스트 가져오기")
    void test2(){
        //given

        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //ls
        List<Store> ls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StoreRequestDto s = new StoreRequestDto();
            s.setName("name is "+i);
            ls.add(new Store(s,user));
        }

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.findAll()).willReturn(ls);

        //then
        List<StoreResponseDto> storeResponseDto = storeService.getAllStore();

        assertEquals(storeResponseDto.size(),ls.size());
    }


}
