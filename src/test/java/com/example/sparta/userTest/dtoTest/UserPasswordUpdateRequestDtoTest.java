package com.example.sparta.userTest.dtoTest;

import com.example.sparta.domain.user.dto.UserPasswordUpdateRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserPasswordUpdateRequestDtoTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Test
    @DisplayName("로그인 비밀번호 수정 입력 테스트 - 성공")
    void UserPasswordUpdateRequestDtoTest() {
        //given
        String password = "test1234";
        String newpassword = "newtest1234";
        String checkpassword = "newtest1234";

        //when
        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto = new UserPasswordUpdateRequestDto(
            password, newpassword, checkpassword);

        //then
        Assertions.assertEquals(password, userPasswordUpdateRequestDto.getPassword());
        Assertions.assertEquals(newpassword, userPasswordUpdateRequestDto.getNewpassword());
        Assertions.assertEquals(checkpassword, userPasswordUpdateRequestDto.getCheckpassword());
    }

    @Test
    @DisplayName("로그인 비밀번호 수정 입력 테스트 - 실패 - 변경 비밀번호 양식 오류")
    void UserPasswordUpdateRequestDtoTest2() {
        //given
        String password = "test1234";
        String newpassword = "newtest123";
        String checkpassword = "new";

        //when
        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto = new UserPasswordUpdateRequestDto(
            password, newpassword, checkpassword);

        //then
        Set<ConstraintViolation<UserPasswordUpdateRequestDto>> violations = validator.validate(
            userPasswordUpdateRequestDto);
        for (ConstraintViolation<UserPasswordUpdateRequestDto> violation : violations) {
            Assertions.assertEquals("must match \"^[a-zA-Z0-9]{8,15}$\"", violation.getMessage());
        }
    }

    @Test
    @DisplayName("로그인 비밀번호 수정 입력 테스트 - 실패 - 변경 비밀번호 양식 오류")
    void UserPasswordUpdateRequestDtoTest3() {
        //given
        String password = "test1234";
        String newpassword = "new";
        String checkpassword = "newtest123";

        //when
        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto = new UserPasswordUpdateRequestDto(
            password, newpassword, checkpassword);

        //then
        Set<ConstraintViolation<UserPasswordUpdateRequestDto>> violations = validator.validate(
            userPasswordUpdateRequestDto);
        for (ConstraintViolation<UserPasswordUpdateRequestDto> violation : violations) {
            Assertions.assertEquals("must match \"^[a-zA-Z0-9]{8,15}$\"", violation.getMessage());
        }
    }
}


