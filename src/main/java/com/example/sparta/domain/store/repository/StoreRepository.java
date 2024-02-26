package com.example.sparta.domain.store.repository;

import com.example.sparta.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store,Long> {
    List<Store> findAllByNameContains(String name);
}
