package com.example.sparta.global.impl;

import com.example.sparta.domain.user.entity.User;
import com.example.sparta.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserDetailsService {

    private final UserRepository userRepository;

    // 이메일 값으로 유저 데이터 베이스에서 해당 값을 찾아와서 UserDetails 를 통해 세부 정보를 저장
    public UserDetails getUserDetails(String email) {
        User user = userRepository.findByEmail(email)
            // 만약 username 을 못찾으면 Not Found 로 던지고,
            .orElseThrow(() -> new UsernameNotFoundException("Not Found " + email));
        // username 과 동일한 정보를 찾으면 해당 user 의 각종 값을 담아서 UserDetails 형태로 반환
        return new UserDetailsImpl(user);
    }
}
