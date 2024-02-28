package com.example.sparta.domain.dibs.dto;

import com.example.sparta.domain.dibs.entity.Dibs;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DibsResponseDto {

    private User user;
    private Store store;

    public DibsResponseDto(Dibs dibs) {
        this.store = dibs.getStore();
        this.user = dibs.getUser();
    }
}
