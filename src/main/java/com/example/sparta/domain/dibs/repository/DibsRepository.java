package com.example.sparta.domain.dibs.repository;

import com.example.sparta.domain.dibs.entity.Dibs;
import com.example.sparta.domain.store.entity.Store;
import com.example.sparta.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DibsRepository extends JpaRepository<Dibs, Long> {

    Optional<Dibs> findDibsByStoreAndUser(Store store, User user);
}
