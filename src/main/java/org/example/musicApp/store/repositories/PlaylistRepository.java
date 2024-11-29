package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutAudio.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
}
