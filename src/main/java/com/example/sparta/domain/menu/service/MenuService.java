package com.example.sparta.domain.menu.service;

import com.example.sparta.domain.menu.dto.GetMenuResponseDto;
import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.menu.repository.MenuRepository;
import com.example.sparta.domain.user.entity.User;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public GetMenuResponseDto getMenu(Long storeId, Long menuId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(
            () -> new NoSuchElementException("해당 메뉴를 찾을 수 없습니다.")
        );

        if (!menu.getStore().getStoreId().equals(storeId)) {
            throw new IllegalArgumentException("메뉴와 가게 정보가 올바르지 않습니다.");
        }

        return GetMenuResponseDto.builder().menuId(menuId).menuName(menu.getName())
            .menuPrice(menu.getPrice()).build();
    }
}
