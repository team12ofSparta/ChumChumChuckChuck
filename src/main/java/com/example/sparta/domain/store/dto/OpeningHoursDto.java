package com.example.sparta.domain.store.dto;

import com.example.sparta.domain.store.entity.Store;
import java.time.LocalTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OpeningHoursDto {

    LocalTime opening;
    LocalTime closing;

    public OpeningHoursDto(Store store) {
        this.opening = store.getOpeningTime();
        this.closing = store.getClosingTime();
    }
}
