package com.example.sparta.domain.store.repository;

import com.example.sparta.domain.store.entity.Store;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findAllByNameContains(String name);
}
