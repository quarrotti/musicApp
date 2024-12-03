package org.example.musicApp.api.services;

import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.ImageDto;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.entities.common.ImageEntity;
import org.example.musicApp.store.repositories.ImageRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final UserService userService;

    public void saveImage(ImageEntity image){
        imageRepository.save(image);
    }

    public ImageEntity findByUser(Principal principal){
        return imageRepository.findByUser(userService.findByLogin(principal));
    }
}
