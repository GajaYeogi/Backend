package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.VisitidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitidRepository extends JpaRepository<VisitidEntity, Long> {
    Optional<VisitidEntity> findByVisitid(String visitid);
}
