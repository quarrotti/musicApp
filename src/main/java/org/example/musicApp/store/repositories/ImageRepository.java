package org.example.musicApp.store.repositories;

import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.entities.common.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    ImageEntity findByUser(UserEntity user);
    void deleteById(Long id);

}
