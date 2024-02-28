package com.example.sparta.userTest.dtoTest;

import com.example.sparta.domain.user.dto.UserPasswordUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserProfileUpdateResponseDto;
import com.example.sparta.domain.user.dto.UserSignupRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserSignupRequestDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Test
    @DisplayName("유저 로그인 필요 데이터 입력 테스트 - 성공")
    void UserSignupRequestDto() {
        //given
        String name = "테스터";
        String password = "test1234";
        String email = "test@test.com";
        String address = "테스트 주소";
        //when
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
            name,password, email, address);

        //then
        Assertions.assertEquals(name, userSignupRequestDto.getName());
        Assertions.assertEquals(password, userSignupRequestDto.getPassword());
        Assertions.assertEquals(email, userSignupRequestDto.getEmail());
        Assertions.assertEquals(address, userSignupRequestDto.getAddress());

    }

    @Test
    @DisplayName("유저 로그인 필요 데이터 입력 테스트 - 실패 - 변경 비밀번호 양식 오류")
    void UserSignupRequestDto2() {
        //given
        String name = "테스터";
        String password = "test1234";
        String email = "test@test.com";
        String address = "테스트 주소";
        //when
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
            name,password, email, address);

        //then
        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(
            userSignupRequestDto);
        for (ConstraintViolation<UserSignupRequestDto> violation : violations) {
            Assertions.assertEquals("must match \"^[a-zA-Z0-9]{8,15}$\"", violation.getMessage());
        }
    }

}
