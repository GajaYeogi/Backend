package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.WriteidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WriteidRepository extends JpaRepository<WriteidEntity, Long> {
    Optional<WriteidEntity> findByWriteid(String writeid);
}
