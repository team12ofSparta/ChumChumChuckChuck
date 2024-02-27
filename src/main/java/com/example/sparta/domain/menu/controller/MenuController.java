package com.example.sparta.domain.menu.controller;

import com.example.sparta.domain.menu.dto.GetMenuResponseDto;
import com.example.sparta.domain.menu.service.MenuService;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/stores/{storeId}/menus/{menuId}")
    public ResponseEntity<ResponseDto<GetMenuResponseDto>> getMenu(@PathVariable Long storeId,
        @PathVariable Long menuId) {

        GetMenuResponseDto getMenuResponseDto = menuService.getMenu(storeId, menuId);

        return ResponseEntity.ok().body(ResponseDto.<GetMenuResponseDto>builder().statusCode(
            HttpStatus.OK.value()).message("상세 메뉴 조회 완료").data(getMenuResponseDto).build());
    }
}
