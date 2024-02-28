package com.example.sparta.userTest.serviceTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.sparta.domain.user.controller.UserController;
import com.example.sparta.domain.user.dto.UserSignupRequestDto;
import com.example.sparta.domain.user.dto.UserSignupResponseDto;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.entity.UserRoleEnum;
import com.example.sparta.domain.user.repository.UserRepository;
import com.example.sparta.domain.user.service.UserService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import com.example.sparta.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private  JwtUtil jwtUtil;
    @Mock
    User user;



    @Test
    @DisplayName("유저 회원 가입 성공 테스트")
    public void userSignupTest01() {
        //given
        String name = "이름";
        String password = "123456789";
        String email = "test@test.com";
        String address = "테스트 주소";

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
            name, password, email, address
        );

        //when

        UserSignupResponseDto response = userService.userSignup(userSignupRequestDto);

        //then
        Assertions.assertEquals(name,response.getName());
        Assertions.assertEquals(email,response.getEmail());
        Assertions.assertEquals(address,response.getAddress());
    }

    @Test
    @DisplayName("유저 회원 가입 실패 테스트 - 이메일 중복")
    public void userSignupTest02() {
        //given
        String name = "이름";
        String password = "123456789";
        String email = "test1test1com";
        String address = "테스트 주소";

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
            name, password, email, address
        );
        user = new User(name,password,email,address, UserRoleEnum.USER,1L);
        given(userRepository.findByEmail(userSignupRequestDto.getEmail())).willReturn(Optional.of(user));

        //when

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,()-> userService.userSignup(userSignupRequestDto));

        //then
       Assertions.assertEquals(e.getMessage(),"이미 가입된 email 입니다");

    }



}
