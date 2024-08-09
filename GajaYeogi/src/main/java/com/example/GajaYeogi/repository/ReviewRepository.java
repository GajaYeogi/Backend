package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.PostEntity;
import com.example.GajaYeogi.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    Optional<ReviewEntity> findByReviewtitle(String reviewtitle);
    Optional<ReviewEntity> findByReviewuser(String reviewuser);
}