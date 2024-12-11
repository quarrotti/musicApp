package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutAudio.AudioEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AudioRepository extends JpaRepository<AudioEntity, Long> {
    @Query("SELECT a FROM AudioEntity a WHERE a.users = :id")
    List<AudioEntity> findAllByUser(@Param("id") Long id);
    @Query("SELECT COUNT(a) FROM AudioEntity a WHERE a. = :id")
    int countAllByUser(@Param("id") Long id);
    @Query("SELECT a FROM AudioEntity a ORDER BY a.createdAt DESC")
    List<AudioEntity> findTop20ByOrderByCreatedAtDesc(Pageable pageable);
    Optional<AudioEntity> findByName(String name);
    //todo all
}
