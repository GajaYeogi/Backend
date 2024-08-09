package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.PostEntity;
import com.example.GajaYeogi.entity.PostWriteidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findByPosttitle(String posttitle);
    Optional<PostEntity> findByPostuser(String postuser);
}