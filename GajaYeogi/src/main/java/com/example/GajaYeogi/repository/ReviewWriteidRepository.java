package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.PostWriteidEntity;
import com.example.GajaYeogi.entity.ReviewWriteidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewWriteidRepository extends JpaRepository<ReviewWriteidEntity, Long> {
    Optional<ReviewWriteidEntity> findByWriteid(String writeid);
}
