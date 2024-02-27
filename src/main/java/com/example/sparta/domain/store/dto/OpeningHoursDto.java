package com.example.sparta.domain.store.dto;

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
}
