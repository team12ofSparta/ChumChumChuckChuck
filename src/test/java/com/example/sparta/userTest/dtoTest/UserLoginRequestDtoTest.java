package com.example.sparta.userTest.dtoTest;

import com.example.sparta.domain.user.dto.UserLoginRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserLoginRequestDtoTest {

    @Test
    @DisplayName("로그인 정보 입력 테스트")
    void UserLoginRequestDto() {
        //given
        String email = "test@test.com";
        String password = "test1234";
        //when
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto(email, password);

        //then
        Assertions.assertEquals(email, userLoginRequestDto.getEmail());
        Assertions.assertEquals(password, userLoginRequestDto.getPassword());

    }

}
