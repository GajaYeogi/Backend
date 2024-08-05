package com.example.GajaYeogi.repository;

import com.example.GajaYeogi.entity.PostWriteidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostWriteidRepository extends JpaRepository<PostWriteidEntity, Long> {
    Optional<PostWriteidEntity> findByWriteid(String writeid);
}
