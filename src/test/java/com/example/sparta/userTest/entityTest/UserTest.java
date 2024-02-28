package com.example.sparta.userTest.entityTest;

import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.entity.UserRoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    @DisplayName("User Entity 테스트")
    void UserTest() {
        //given
        String name = "테스터";
        String password = "test1234";
        String email = "test@test.com";
        String address = "테스트 주소";

        //when
        User user = new User(name, password, email, address, UserRoleEnum.USER, 1L);

        // then

        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(password, user.getPassword());
        Assertions.assertEquals(email, user.getEmail());
        Assertions.assertEquals(address, user.getAddress());

    }
}
