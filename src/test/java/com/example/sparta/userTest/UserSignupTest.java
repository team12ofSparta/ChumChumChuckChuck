package com.example.sparta.userTest;

import static org.mockito.ArgumentMatchers.any;

import com.example.sparta.domain.user.controller.UserController;
import com.example.sparta.domain.user.dto.UserSignupRequestDto;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.service.KakaoUserService;
import com.example.sparta.domain.user.service.UserService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.jwt.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserSignupTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private  UserService userService;
    @Mock
    private  JwtUtil jwtUtil;
    @Mock
    private  KakaoUserService kakaoUserService;
    @Mock
        private User user;
    MockMvc mockMvc;
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Test
    @DisplayName("유저 회원 가입 성공 테스트")
    public void userSignupTest01() {
        //given  정상적으로 유저 정보 주입

        String name = "이름";
        String password = "123456789";
        String email = "test@test.com";
        String address = "테스트 주소";
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
            name,password,email,address
        );

        //when

        ResponseEntity<ResponseDto<Void>> response  =  userController.usersSignup(userSignupRequestDto);
        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(201),response.getStatusCode());
    }

    @Test
    @DisplayName("유저 회원 가입 실패 테스트 - 이메일 중복")
    public void userSignupTest02() {
        //given  정상적으로 유저 정보 주입

        String name = "이름";
        String password = "123456789";
        String email = "test@test.com";
        String address = "테스트 주소";
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
            name,password,email,address
        );
        String name2 = "이름2";
        String password2 = "123456789";
        String email2 = "test@test.com";
        String address2 = "테스트 주소2";
        UserSignupRequestDto userSignupRequestDto2 = new UserSignupRequestDto(
            name2,password2,email2,address2
        );

        //when

        ResponseEntity<ResponseDto<Void>> response  =  userController.usersSignup(userSignupRequestDto);
        ResponseEntity<ResponseDto<Void>> response2  =  userController.usersSignup(userSignupRequestDto2);
        //then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(201),response.getStatusCode());
        Assertions.assertNotNull(response2);
        Assertions.assertEquals(HttpStatusCode.valueOf(201),response2.getStatusCode());
    }
}
