package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutAudio.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface AudioRepository extends JpaRepository<AudioEntity, Long> {
    @Query("SELECT a FROM AudioEntity a JOIN a.users u WHERE u.id = :userId")
    List<AudioEntity> findAllByUsers(@Param("userId") Long id);
    List<AudioEntity> findAllByNameAndGenres(String name, Genre genre);
    List<AudioEntity> findAllByName(String name);
    List<AudioEntity> findAllByGenres(Genre genre);
    @Query("SELECT COUNT(a) FROM AudioEntity a JOIN a.users u WHERE u.id = :userId")
    Long countByUserId(@Param("userId") Long userId);
    @Query("SELECT a FROM AudioEntity a ORDER BY a.createdAt DESC")
    List<AudioEntity> findTop20ByOrderByCreatedAtDesc(Pageable pageable);
    Optional<AudioEntity> findByName(String name);

    void deleteById(Long id);
}
