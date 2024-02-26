package com.example.sparta.domain.user.controller;



import com.example.sparta.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    //Service 주입받기
    private final UserService userService;


    // 회원 가입 하기



}
