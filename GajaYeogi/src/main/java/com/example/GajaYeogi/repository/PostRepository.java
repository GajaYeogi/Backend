package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}