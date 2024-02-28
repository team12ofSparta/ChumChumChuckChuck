package com.example.sparta.domain.user.entity;

import com.example.sparta.domain.user.dto.UserPasswordUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserProfileUpdateRequestDto;
import com.example.sparta.domain.user.dto.UserSignupRequestDto;
import com.example.sparta.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;

    @Column(nullable = false)
    private String address;

    private Long kakaoId;

    public User(UserSignupRequestDto userSignupRequestDto,PasswordEncoder passwordEncoder) {
        this.name = userSignupRequestDto.getName();
        this.password = passwordEncoder.encode(userSignupRequestDto.getPassword());
        this.email = userSignupRequestDto.getEmail();
        this.address = userSignupRequestDto.getAddress();
        this.role =  UserRoleEnum.USER;
    }
    public User(String name, String password, String email, String address, UserRoleEnum role, Long kakaoId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.role = role;
        this.kakaoId = kakaoId;
    }
    public User(String name, String password, String email, String address) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public void userUpdate(UserProfileUpdateRequestDto userProfileReworkRequestDto) {
        if(userProfileReworkRequestDto.getName()!=null){
            this.name = userProfileReworkRequestDto.getName();
        }
        if(userProfileReworkRequestDto.getAddress() != null){
            this.address = userProfileReworkRequestDto.getAddress();
        }
    }
    public void userPasswordUpdate(
        UserPasswordUpdateRequestDto userPasswordUpdateRequestDto,
        PasswordEncoder passwordEncoder) {

        if (userPasswordUpdateRequestDto.getNewpassword() != null) {
            this.password = passwordEncoder.encode(userPasswordUpdateRequestDto.getNewpassword());
        }
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}
