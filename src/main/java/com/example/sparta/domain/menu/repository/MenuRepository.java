package com.example.sparta.domain.menu.repository;

import com.example.sparta.domain.menu.entity.Menu;
import com.example.sparta.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m where m.name = ?1 and m.store = ?2")
    boolean findByMenuNameAndStore(String menuName, Store store);
}
