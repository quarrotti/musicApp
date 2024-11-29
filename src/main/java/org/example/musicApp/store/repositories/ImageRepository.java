package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.common.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
