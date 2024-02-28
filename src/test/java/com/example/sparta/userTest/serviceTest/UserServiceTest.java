package com.example.sparta.userTest.serviceTest;


import static org.mockito.BDDMockito.given;

import com.example.sparta.domain.user.dto.UserProfileUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserProfileUpdateResponseDto;
import com.example.sparta.domain.user.dto.UserSignupRequestDto;
import com.example.sparta.domain.user.dto.UserSignupResponseDto;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.entity.UserRoleEnum;
import com.example.sparta.domain.user.repository.UserRepository;
import com.example.sparta.domain.user.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    User user;

    @Mock
    HttpServletResponse httpServletResponse;


    @Test
    @DisplayName("유저 회원 가입 테스트 - 성공")
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
        Assertions.assertEquals(name, response.getName());
        Assertions.assertEquals(email, response.getEmail());
        Assertions.assertEquals(address, response.getAddress());
    }

    @Test
    @DisplayName("유저 회원 가입 테스트 - 실패 - 이메일 중복")
    public void userSignupTest02() {
        //given
        String name = "이름";
        String password = "123456789";
        String email = "test@test.com";
        String address = "테스트 주소";

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(
            name, password, email, address
        );
        user = new User(name, password, email, address, UserRoleEnum.USER, 1L);
        given(userRepository.findByEmail(userSignupRequestDto.getEmail())).willReturn(
            Optional.of(user));

        //when

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
            () -> userService.userSignup(userSignupRequestDto));

        //then
        Assertions.assertEquals(e.getMessage(), "이미 가입된 email 입니다");

    }

    @Test
    @DisplayName("회원 정보 수정 테스트 - 성공")
    public void userProfileUpdateTest01() {
        //given
        String name = "이름";
        String password = "123456789";
        String email = "test@test.com";
        String address = "테스트 주소";

        String nameUp = "수정 이름";
        String addressUp = "수정 주소";

        UserProfileUpdateRequestDto userProfileUpdateRequestDto = new UserProfileUpdateRequestDto(
            nameUp, addressUp);
        user = new User(name, password, email, address, UserRoleEnum.USER, 1L);

        given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));
        //when
        UserProfileUpdateResponseDto response = userService.userProfileUpdate(
            userProfileUpdateRequestDto, user);
        //then
        Assertions.assertEquals(nameUp, response.getName());
        Assertions.assertEquals(email, response.getEmail());
        Assertions.assertEquals(addressUp, response.getAddress());

    }

    @Test
    @DisplayName("회원 정보 수정 테스트 - 실패 - 로그인 안하고 진입할때")
    public void userProfileUpdateTest02() {
        //given
        String name = "이름";
        String password = "123456789";
        String email = "test@test.com";
        String address = "테스트 주소";

        String nameUp = "수정 이름";
        String addressUp = "수정 주소";

        UserProfileUpdateRequestDto userProfileUpdateRequestDto = new UserProfileUpdateRequestDto(
            nameUp, addressUp);

        //when
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
            () -> userService.userProfileUpdate(userProfileUpdateRequestDto, user));
        //then
        Assertions.assertEquals(e.getMessage(), "로그인 유저 정보가 없습니다.");

    }


}
