package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface AudioRepository extends JpaRepository<AudioEntity, Long> {

    List<AudioEntity> findAllByUser(UserEntity user);
    int countAudioEntitiesByUser(UserEntity user);
    int countAllByUserId(Long id);

    @Query(value = "SELECT a FROM AudioEntity a ORDER BY a.createdAt DESC")
    List<AudioEntity> findTop20ByOrderByCreatedAtDesc(Pageable pageable);
    Optional<AudioEntity> findByName(String name);
}
