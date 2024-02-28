package com.example.sparta.dibsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.sparta.domain.dibs.dto.DibsDto;
import com.example.sparta.domain.dibs.entity.Dibs;
import com.example.sparta.domain.dibs.repository.DibsRepository;
import com.example.sparta.domain.dibs.service.DibsService;
import com.example.sparta.domain.store.dto.CreateStoreRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DibsServiceTest {
  @Mock
  DibsRepository mockdibsRepository;
  @Mock
  StoreRepository mockStoreRepository;
  @Test
  @DisplayName("찜 생성")
  void test1(){

    //given
    //스토어 request
    CreateStoreRequestDto requestDto = new CreateStoreRequestDto();
    requestDto.setName("service name");
    requestDto.setCategory("Chicken");

    // 유저
    User user = new User();
    user.setUserId(1L);
    user.setAddress("로마");
    user.setEmail("sparta@iscool.com");
    user.setName("스탄이");

    //score for given
    Store store = new Store(requestDto,user);
    //dips for given
    Dibs dibs = new Dibs(user,store);

    //when
    DibsService service = new DibsService(mockStoreRepository,mockdibsRepository);

    given(mockStoreRepository.findById(1L)).willReturn(Optional.of(store));

    DibsDto answer = service.createDibs(1L,user);

    //then
    assertEquals(dibs.getUser(),answer.getUser());
  }
  @Test
  @DisplayName("찜 제거")
  void test2(){
    //given
    //스토어 request
    CreateStoreRequestDto requestDto = new CreateStoreRequestDto();
    requestDto.setName("service name");
    requestDto.setCategory("Chicken");

    // 유저
    User user = new User();
    user.setUserId(1L);
    user.setAddress("로마");
    user.setEmail("sparta@iscool.com");
    user.setName("스탄이");

    //score for given
    Store store = new Store(requestDto,user);
    //dibs for given
    Dibs dibs = new Dibs(user,store);
    //when
    DibsService service = new DibsService(mockStoreRepository,mockdibsRepository);

    given(mockStoreRepository.findById(1L)).willReturn(Optional.of(store));
    given(mockdibsRepository.findDibsByStoreAndUser(any(),any())).willReturn(Optional.of(dibs));

    Long ans = service.deleteDibs(1L,user);

    //then
    assertEquals(ans,1L);

  }
}
