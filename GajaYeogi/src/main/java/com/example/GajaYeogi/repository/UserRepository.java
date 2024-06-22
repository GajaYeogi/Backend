package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUser(String user);
    @Query("SELECT u FROM UserEntity u WHERE SIZE(u.scraps) > 0")
    Optional<UserEntity> findUserWithNonEmptyScraps();
}
