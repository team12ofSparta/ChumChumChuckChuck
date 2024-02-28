package com.example.sparta.domain.menu.controller;

import com.example.sparta.domain.menu.dto.AddMenuRequestDto;
import com.example.sparta.domain.menu.dto.AddMenuResponseDto;
import com.example.sparta.domain.menu.dto.GetMenuResponseDto;
import com.example.sparta.domain.menu.service.MenuService;
import com.example.sparta.global.dto.ResponseDto;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            HttpStatus.OK.value()).data(getMenuResponseDto).build());
    }

    @PostMapping("/stores/{storeId}/menus")
    public ResponseEntity<ResponseDto<AddMenuResponseDto>> addMenu(@PathVariable Long storeId,
        @Valid @RequestBody AddMenuRequestDto requestDto) {

        AddMenuResponseDto responseDto = menuService.addMenu(storeId, requestDto);

        URI location = URI.create(
            String.format("/v1/stores/%d/menus/%d", storeId, responseDto.getMenuId()));

        return ResponseEntity.created(location).body(
            ResponseDto.<AddMenuResponseDto>builder().statusCode(HttpStatus.CREATED.value())
                .data(responseDto).build());
    }
}
