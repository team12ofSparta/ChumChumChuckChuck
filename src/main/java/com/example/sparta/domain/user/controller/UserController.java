package com.example.sparta.domain.user.controller;


import com.example.sparta.domain.user.dto.UserLoginRequestDto;
import com.example.sparta.domain.user.dto.UserPasswordUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserProfileUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserSignupRequestDto;
import com.example.sparta.domain.user.service.KakaoUserService;
import com.example.sparta.domain.user.service.UserService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import com.example.sparta.global.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    //Service 주입받기
    private final UserService userService;
    //JwtUtil 주입받기
    private final JwtUtil jwtUtil;
    private final KakaoUserService kakaoUserService;


    // 회원 가입 하기
    @PostMapping("/users/signup")
    public ResponseEntity<ResponseDto<Void>> usersSignup(
        @Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {
        userService.userSignup(userSignupRequestDto);
        return ResponseEntity.status(201).body(ResponseDto.
            <Void>builder()
            .statusCode(HttpStatus.CREATED.value())
            .message("회원가입 성공")
            .data(null)
            .build()
        );
    }

    // 로그인 하기
    @PostMapping("/users/login")
    public ResponseEntity<ResponseDto<Void>> userLogin(
        @RequestBody UserLoginRequestDto userLoginRequestDto,
        HttpServletResponse httpServletResponse) {
        userService.userLogin(userLoginRequestDto, httpServletResponse);
        return ResponseEntity.status(200).body(ResponseDto.<Void>builder()
            .statusCode(HttpStatus.OK.value())
            .message("로그인 성공")
            .data(null)
            .build()
        );
    }


    //로그아웃 하기
    @GetMapping("/users/logout")
    public ResponseEntity<ResponseDto<Void>> userLogout(
        HttpServletResponse httpServletResponse
    ) {
        userService.userLogout(httpServletResponse);
        return ResponseEntity.status(200).body(ResponseDto.<Void>builder()
            .statusCode(HttpStatus.OK.value())
            .message("로그아웃 성공")
            .data(null)
            .build()
        );
    }

    //유저 정보 수정하기 (이름, 주소)
    @PatchMapping("/users")
    public ResponseEntity<ResponseDto<Void>> userProfileUpdate(
        @RequestBody UserProfileUpdateRequestDto userProfileUpdateRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.userProfileUpdate(userProfileUpdateRequestDto, userDetails.getUser());
        return ResponseEntity.status(200).body(ResponseDto.<Void>builder()
            .statusCode(HttpStatus.OK.value())
            .message("회원정보 수정 성공")
            .data(null)
            .build()
        );
    }

    //유저 정보 수정하기 (비밀번호)
    @PatchMapping("/users/password")
    public ResponseEntity<ResponseDto<Void>> userPasswordUpdate(
        @RequestBody UserPasswordUpdateRequestDto userPasswordUpdateRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.userPasswordUpdate(userPasswordUpdateRequestDto, userDetails.getUser());
        return ResponseEntity.status(200).body(ResponseDto.<Void>builder()
            .statusCode(HttpStatus.OK.value())
            .message("회원정보 수정 성공")
            .data(null)
            .build()
        );
    }


    // 카카오 로그인
    @GetMapping("/users/kakao")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse httpServletResponse)
        throws JsonProcessingException {
        String token = kakaoUserService.kakaoLogin(code);   // jwt 토큰을 쿠키에 넣어주는 작업 해서 response 에 넣어줌
        Cookie cookie = new Cookie(jwtUtil.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        return "redirect:/";
    }


}