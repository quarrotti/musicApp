package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AudioRepository extends JpaRepository<AudioEntity, Long> {
}
