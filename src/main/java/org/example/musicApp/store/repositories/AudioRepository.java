package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AudioRepository extends JpaRepository<AudioEntity, Long> {

    List<AudioEntity> findAllByUser(UserEntity user);
}
