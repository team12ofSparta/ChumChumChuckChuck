package com.example.sparta.userTest.dtoTest;

import com.example.sparta.domain.user.dto.UserProfileUpdateResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserProfileUpdateResponseDtoTest {
    @Test
    @DisplayName("유저 정보 수정 완료 응답 데이터 테스트")
    void UserProfileUpdateResponseDto() {
        //given
        String name = "테스터";
        String email = "test@test.com";
        String address = "테스트 주소";
        //when
        UserProfileUpdateResponseDto userProfileUpdateResponseDto = new UserProfileUpdateResponseDto(
            name, email, address);

        //then
        Assertions.assertEquals(name, userProfileUpdateResponseDto.getName());
        Assertions.assertEquals(email, userProfileUpdateResponseDto.getEmail());
        Assertions.assertEquals(address, userProfileUpdateResponseDto.getAddress());

    }

}
