package org.example.musicApp.api.services;

import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.ImageDto;
import org.example.musicApp.store.entities.common.ImageEntity;
import org.example.musicApp.store.repositories.ImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void saveImage(ImageEntity image){
        imageRepository.save(image);
    }
}
