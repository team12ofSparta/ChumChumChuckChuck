package com.example.sparta.domain.menu.service;

import com.example.sparta.domain.menu.dto.AddMenuRequestDto;
import com.example.sparta.domain.menu.dto.AddMenuResponseDto;
import com.example.sparta.domain.menu.dto.GetMenuResponseDto;
import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.menu.repository.MenuRepository;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.store.repository.StoreRepository;
import com.example.sparta.domain.user.entity.User;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

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

    public AddMenuResponseDto addMenu(Long storeId, AddMenuRequestDto requestDto) {

        Store store = storeRepository.findById(storeId).orElseThrow(
            () -> new NoSuchElementException("해당 가게를 찾을 수 없습니다.")
        );

        if (menuRepository.findByMenuNameAndStore(requestDto.getMenuName(), store)) {
            throw new IllegalArgumentException("해당 가게에서 메뉴 이름이 이미 존재합니다.");
        }

        Menu menu = menuRepository.save(
            new Menu(requestDto.getMenuName(), requestDto.getMenuPrice(), store));

        return AddMenuResponseDto.builder().menuId(menu.getMenuId()).menuName(menu.getName())
            .menuPrice(menu.getPrice()).storeId(storeId).storeName(store.getName()).build();
    }
}
