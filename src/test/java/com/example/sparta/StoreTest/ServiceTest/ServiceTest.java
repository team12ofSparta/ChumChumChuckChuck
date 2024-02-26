package com.example.sparta.StoreTest.ServiceTest;

import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.dto.StoreResponseDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.store.service.StoreService;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //score
        Store score = new Store(requestDto,user);

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.save(any())).willReturn(score);

        //then
        StoreResponseDto storeResponseDto = storeService.createStore(requestDto,user);

        assertEquals(score.getName(),storeResponseDto.getName());
    }

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

    @Test
    @DisplayName("스토어 수정 하기")
    void test3(){
        //given
        //스토어 request
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setName("new name");
        requestDto.setCategory("Chicken");
        requestDto.setDibsCount(100);
        requestDto.setRating(5.5f);

        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //score
        Store score = new Store(requestDto,user);

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.findById(1L)).willReturn(Optional.of(score));
        Long id = storeService.editStore(1L,requestDto,user);
        //then
        assertEquals(1L,id);
    }
    @Test
    @DisplayName("스토어 삭제 하기")
    void test4(){
        //given

        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.findById(1L)).willReturn(Optional.of(new Store()));
        Long id = storeService.deleteStore(1L,user);
        //then
        assertEquals(1L,id);
    }
    @Test
    @DisplayName("스토어 삭제 하기")
    void test5(){
        //given

        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.findById(1L)).willReturn(Optional.of(new Store()));
        Long id = storeService.deleteStore(1L,user);
        //then
        assertEquals(1L,id);
    }

    @Test
    @DisplayName("스토어 선택 검색 하기")
    void test6(){
        //given
        //스토어 request
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setName("new name");
        requestDto.setCategory("Chicken");
        requestDto.setDibsCount(100);
        requestDto.setRating(5.5f);

        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //score
        Store score = new Store(requestDto,user);

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.findById(1L)).willReturn(Optional.of(score));
        StoreResponseDto storeResponseDto = storeService.getStoreById(1L);
        //then
        assertEquals(storeResponseDto.getName(),score.getName());
    }
    @Test
    @DisplayName("스토어 이름으로 검색 하기")
    void test7(){
        //given
        //스토어 request
        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setName("new name");
        requestDto.setCategory("Chicken");
        requestDto.setDibsCount(100);
        requestDto.setRating(5.5f);


        // 유저
        User user = new User();
        user.setUserId(1L);
        user.setAddress("로마");
        user.setEmail("sparta@iscool.com");
        user.setName("스탄이");

        //score
        Store score = new Store(requestDto,user);

        //ls
        List<Store> ls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ls.add(score);
        }

        //service
        StoreService storeService = new StoreService(MockStoreRepository,userRepository);

        // when
        given(MockStoreRepository.findAllByNameContains("new name")).willReturn(ls);
        List<StoreResponseDto> storeResponseDtoLs = storeService.getAllStoreByName("new name");
        //then
        assertEquals(storeResponseDtoLs.get(1).getName(),ls.get(1).getName());
    }
}
