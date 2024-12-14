package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
    @Query("SELECT u FROM UserEntity u ORDER BY SIZE(u.audios) DESC")
    List<UserEntity> findTop10UsersByAudioCount(Pageable pageable);

    Optional<UserEntity> findByUsername(String username);



}
