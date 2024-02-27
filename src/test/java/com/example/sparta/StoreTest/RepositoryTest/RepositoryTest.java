package com.example.sparta.storeTest.repositoryTest;

import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
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
    @Test
    @DisplayName("스토어 테스트")
    void test1(){
    //given
    StoreRequestDto requestDto = new StoreRequestDto();
    requestDto.setName("스토어이름");

    User user = new User();
    user.setName("유저이름");
    user.setUserId(1L);
    user.setAddress("레포주소");
    user.setEmail("repo@sparta.com");

    Store store = new Store(requestDto,user);
    //when
    //create
        Store save = storeRepository.save(store);
        assertEquals("스토어이름",save.getName());

    //read
        Store save2 = storeRepository.findById(1L).orElseThrow(()-> new NoSuchElementException("Repo Read Test 유저 찾을수 없음"));
         assertEquals("스토어이름",save.getName());
    //update
        requestDto.setName("업테이트 된 이름");
        requestDto.setContent("업테이트 된");
        save2.update(requestDto);
        assertEquals("업테이트 된 이름",save2.getName());
    //delete
        storeRepository.delete(save2);
        try{
            storeRepository.findById(1L).orElseThrow(()-> new NoSuchElementException("해당 스토어 없음"));
        }catch (NoSuchElementException e){
            assertTrue(true);
        }

    // search by name
        try {
            List<Store> save3 = storeRepository.findAllByNameContains("유저이름");
            assertEquals("스토어이름",save3.get(1).getName());
        }
        catch (NoSuchElementException e){
            fail();
        }
    }
}
