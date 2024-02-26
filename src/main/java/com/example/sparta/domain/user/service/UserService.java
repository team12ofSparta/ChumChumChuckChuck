package com.example.sparta.domain.user.service;


import com.example.sparta.domain.user.dto.UserLoginRequestDto;
import com.example.sparta.domain.user.dto.UserLoginResponseDto;
import com.example.sparta.domain.user.dto.UserPasswordUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserPasswordUpdateResponseDto;
import com.example.sparta.domain.user.dto.UserProfileUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserProfileUpdateResponseDto;
import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.entity.UserRoleEnum;
import com.example.sparta.domain.user.repository.UserRepository;
import com.example.sparta.domain.user.dto.UserSignupRequestDto;
import com.example.sparta.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    //Repository 주입
    private final UserRepository userRepository;
    // 비밀번호 인코더 주입
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void userSignup(UserSignupRequestDto userSignupRequestDto) {
        String password = passwordEncoder.encode(userSignupRequestDto.getPassword());
        String email = userSignupRequestDto.getEmail();

        // 이미 가입한 유저인지 체크하기
        if(userRepository.findByEmail(email).isPresent()){
            // 만약 DB에 동일한 Email 이 존재하면
            throw new IllegalArgumentException("이미 가입된 email 입니다");
        }
        User user = new User(userSignupRequestDto.getName(),password,email,userSignupRequestDto.getAddress(),
            UserRoleEnum.USER);
        // if문 안들어가고 잘 넘어오면 입력받아온 Name, password(인코딩한), email, Address 를 user 에 저장

        userRepository.save(user);
        // 해당 user 를 Repository 를 통해서 DB에 저장.
    }

    public void userLogin(UserLoginRequestDto userLoginRequestDto) {
        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();

        User user = userRepository.findByEmail(email)
            .orElseThrow(()-> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getName(), user.getRole());
        // 유저정보에서 이름값을 가져와서 해당 유저의 권한과 함께 넣어 토큰을 만듬.

        UserLoginResponseDto.builder()
            .userId(user.getUserId())
            .password(user.getPassword())
            .email(user.getEmail())
            .name(user.getName())
            .address(user.getAddress())
            .token(token)
            .build();
    }

    public void userLogout(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Authorization",null);
    }

    public void userProfileUpdate(UserProfileUpdateRequestDto userProfileUpdateRequestDto, User user) {
        User userUp = userRepository.findByEmail(user.getEmail())
            .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        userUp.userUpdate(userProfileUpdateRequestDto);
        userRepository.save(userUp);

        UserProfileUpdateResponseDto.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .name(user.getName())
            .address(user.getAddress())
            .build();

    }

    public void userPasswordUpdate(UserPasswordUpdateRequestDto userPasswordUpdateRequestDto, User user) {
        User userUp = userRepository.findByEmail(user.getEmail())
            .orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));
        if(userPasswordUpdateRequestDto.getPassword()==null){
            throw new IllegalArgumentException("현재 비밀번호를 입력해 주세요");
        }
        if(!passwordEncoder.matches(userPasswordUpdateRequestDto.getPassword(),user.getPassword())){
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        if(userPasswordUpdateRequestDto.getNewpassword()==null){
            throw new IllegalArgumentException("변경하는 비밀번호를 입력해 주세요");
        }
        if(userPasswordUpdateRequestDto.getNewpassword()!= null){
            if(!userPasswordUpdateRequestDto.getNewpassword().equals(userPasswordUpdateRequestDto.getCheckpassword())){
                throw new IllegalArgumentException("변경하는 비밀번호가 일치하지 않습니다");
            }
        }

        userUp.userPasswordUpdate(userPasswordUpdateRequestDto,passwordEncoder);
        userRepository.save(userUp);

        UserPasswordUpdateResponseDto.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .name(user.getName())
            .password(user.getPassword())
            .address(user.getAddress())
            .build();

    }

    // 카카오 회원 가입하기
}