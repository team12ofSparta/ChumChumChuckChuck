package com.example.sparta.storeTest.repositoryTest;

import com.example.sparta.domain.store.dto.OpeningHoursDto;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.entity.StoreStatus;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.repository.UserRepository;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
public class RepositoryTest {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    UserRepository userRepository;
    @Test
    @DisplayName("스토어 테스트")
    void test1(){
    //given
    StoreRequestDto requestDto = new StoreRequestDto();
    requestDto.setName("스토어이름");
    requestDto.setRating(3.4f);
    requestDto.setDibsCount(100);
    requestDto.setDeliveryAddress("배달주소");
    requestDto.setAddress("주소");
    requestDto.setCategory("중국집");
    requestDto.setReviewCount(100);

    User user = new User();
    user.setName("유저이름");
    user.setUserId(1L);
    user.setAddress("레포주소");
    user.setEmail("repo@sparta.com");
    //user = userRepository.findById(1L).orElseThrow();

    Store store = new Store(requestDto,user);
    //when
    //create
        Store save = storeRepository.save(store);
        assertEquals("스토어이름",save.getName());

    //read
        List<Store> storeList = storeRepository.findAll();
         Store save2 = storeList.get(0);
         assertEquals("스토어이름",save.getName());

    //update
        requestDto.setName("업테이트 된 이름");
        requestDto.setContent("업테이트 된");
        save2.update(requestDto);
        assertEquals("업테이트 된 이름",save2.getName());

    // search by name
        try {
            List<Store> save3 = storeRepository.findAllByNameContains("업테");
            assertEquals("업테이트 된 이름",save3.get(0).getName());
        }
        catch (NoSuchElementException e){
            fail();
        }

    // open / close store

        store = storeRepository.findById(save2.getStoreId()).orElseThrow(()-> new NoSuchElementException("스토어를 찾을수 없음"));
        store.openStore(true);

        Store store2 = storeRepository.findById(save2.getStoreId()).orElseThrow(()-> new NoSuchElementException("스토어를 찾을수 없음"));
        assertEquals(StoreStatus.RUNNING,store2.getStatus());

    //  Opening hours
        OpeningHoursDto dto = new OpeningHoursDto();
        dto.setOpening(LocalTime.of(1,1,1));
        dto.setClosing(LocalTime.of(11,1,1));
        store = storeRepository.findById(save2.getStoreId()).orElseThrow(()-> new NoSuchElementException("스토어를 찾을수 없음"));
        store.setOpeningHours(dto);

        store2 = storeRepository.findById(save2.getStoreId()).orElseThrow(()-> new NoSuchElementException("스토어를 찾을수 없음"));
        assertEquals(LocalTime.of(11,1,1),store2.getClosingTime());


    // ADMINISTRATOR FORCE CHANGE STORE STATUS!!!
        store = storeRepository.findById(save2.getStoreId()).orElseThrow(()-> new NoSuchElementException("스토어를 찾을수 없음"));
        store.updateStatus(5);

        store2 = storeRepository.findById(save2.getStoreId()).orElseThrow(()-> new NoSuchElementException("스토어를 찾을수 없음"));
        assertEquals(StoreStatus.PERMANENT_BANNED,store2.getStatus());





    //delete
        storeRepository.delete(save2);
        try{
            storeRepository.findById(1L).orElseThrow(()-> new NoSuchElementException("해당 스토어 없음"));
        }catch (NoSuchElementException e){
            assertTrue(true);
        }
    }
}
