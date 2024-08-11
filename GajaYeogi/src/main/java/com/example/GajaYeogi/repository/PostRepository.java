package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.PostEntity;
import com.example.GajaYeogi.entity.PostWriteidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query("SELECT p FROM PostEntity p WHERE p.posttitle LIKE %:posttitle%")
    List<PostEntity> findByPosttitleContaining(@Param("posttitle") String posttitle);
    Optional<PostEntity> findByPostusername(String postusername);
}