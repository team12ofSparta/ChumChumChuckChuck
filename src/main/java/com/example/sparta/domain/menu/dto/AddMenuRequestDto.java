package com.example.sparta.domain.menu.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddMenuRequestDto {

    @NotEmpty(message = "메뉴 이름을 입력해주세요.")
    @Size(min = 1, max = 50, message = "메뉴 이름은 1자 이상 50자 이하여야 합니다.")
    private String menuName;

    @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
    private Long menuPrice;
}
