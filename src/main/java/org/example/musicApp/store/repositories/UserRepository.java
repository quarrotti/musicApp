package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
    @Query(value = "SELECT * FROM app_user ORDER BY SIZE(u.tracks) DESC")
    List<UserEntity> findTop10UsersByAudioCount();

}
