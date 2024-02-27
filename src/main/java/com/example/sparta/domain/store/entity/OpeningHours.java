package com.example.sparta.domain.store.entity;

import com.example.sparta.domain.store.dto.OpeningHoursDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "openinghour")
public class OpeningHours {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalTime opening_time;
  @Column(nullable = false)
  private  LocalTime closing_time;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private Store store;

  public OpeningHours(OpeningHoursDto requestDto,Store store){
    this.opening_time = requestDto.getOpening();
    this.closing_time = requestDto.getClosing();
    this.store = store;
  }
  @Transactional
  public void update(OpeningHoursDto requestDto){
    this.opening_time = requestDto.getOpening();
    this.closing_time = requestDto.getClosing();
  }
}
