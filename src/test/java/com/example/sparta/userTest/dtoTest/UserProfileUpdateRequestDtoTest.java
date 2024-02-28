package com.example.sparta.userTest.dtoTest;

import com.example.sparta.domain.user.dto.UserProfileUpdateRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserProfileUpdateRequestDtoTest {

    @Test
    @DisplayName("유저 정보 수정 데이터 입력 테스트")
    void UserProfileUpdateRequestDto() {
        //given
        String name = "테스터";
        String address = "테스트 주소";
        //when
        UserProfileUpdateRequestDto userProfileUpdateRequestDto = new UserProfileUpdateRequestDto(
            name, address);

        //then
        Assertions.assertEquals(name, userProfileUpdateRequestDto.getName());
        Assertions.assertEquals(address, userProfileUpdateRequestDto.getAddress());

    }
}
