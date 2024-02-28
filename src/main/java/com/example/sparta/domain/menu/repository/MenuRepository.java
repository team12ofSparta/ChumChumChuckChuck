package com.example.sparta.domain.menu.repository;

import com.example.sparta.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
