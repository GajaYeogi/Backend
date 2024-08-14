package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.PostEntity;
import com.example.GajaYeogi.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByOriginalpostid(String originalpostid);
}