package com.example.sparta.domain.store.repository;

import com.example.sparta.domain.store.entity.OpeningHours;
import com.example.sparta.domain.store.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OpeningHoursRepository extends JpaRepository<OpeningHours,Long> {

  Optional<OpeningHours> findStoreOpeningHoursByStore(Store store);
}
