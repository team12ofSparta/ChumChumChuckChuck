package com.example.sparta.userTest.dtoTest;

import com.example.sparta.domain.user.dto.UserSignupResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserSignupResponseDtoTest {

    @Test
    @DisplayName("로그인 로그인 응답 데이터 테스트")
    void UserSignupResponseDto() {
        //given
        String name = "테스터";
        String email = "test@test.com";
        String address = "테스트 주소";

        //when
        UserSignupResponseDto userSignupResponseDto = new UserSignupResponseDto(
            name, email, address);

        //then
        Assertions.assertEquals(name, userSignupResponseDto.getName());
        Assertions.assertEquals(email, userSignupResponseDto.getEmail());
        Assertions.assertEquals(address, userSignupResponseDto.getAddress());
    }


}
