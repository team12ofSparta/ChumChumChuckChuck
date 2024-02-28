package com.example.sparta.dibsTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.sparta.domain.dibs.entity.Dibs;
import com.example.sparta.domain.dibs.repository.DibsRepository;
import com.example.sparta.domain.store.dto.StoreRequestDto;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
    connection = EmbeddedDatabaseConnection.H2)
public class DibsRepositoryTest {

    @Autowired
    StoreRepository storeRepository;
    @Autowired
    DibsRepository dibsRepository;

    @Test
    @DisplayName("찜 테스트")
    void test1() {
        //given

        StoreRequestDto requestDto = new StoreRequestDto();
        requestDto.setName("스토어이름1");
        requestDto.setRating(6.4f);
        requestDto.setDibsCount(1002);
        requestDto.setDeliveryAddress("배달주소1");
        requestDto.setAddress("주소1");
        requestDto.setCategory("중국집");
        requestDto.setReviewCount(100);

        User user = new User();
        user.setName("유저이름");
        user.setUserId(1L);
        user.setAddress("레포주소");
        user.setEmail("repo@sparta.com");

        Store store = new Store(requestDto, user);
        Store save = storeRepository.save(store);
        Long id = save.getStoreId();

        //when
        //create dibs
        Store findStore = storeRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("해당 가계는 찾을수 없어요"));
        Dibs dibs = new Dibs(user, findStore);
        Dibs savedDibs = dibsRepository.save(dibs);
        assertEquals(savedDibs.getUser().getName(), user.getName());
        assertEquals(savedDibs.getStore().getName(), store.getName());

        //remove div

        Dibs removedibs = dibsRepository.findDibsByStoreAndUser(store, user)
            .orElseThrow(() -> new NoSuchElementException("해당 가계는 찾을수 없어요"));
        dibsRepository.delete(removedibs);
        // 만약에 다시 검색 했을때 해당 dibs 을 찾을수 없으면 성공!~
        boolean dib_is_removed = false;
        try {
            dibsRepository.findById(id).orElseThrow(() -> new NoSuchElementException("성공"));
        } catch (NoSuchElementException e) {
            dib_is_removed = true;
            System.out.println("dibs 가 삭제되어 더이상 찾을수 없습니다");
        }
        assertEquals(true, dib_is_removed);
    }
}
