package org.example.musicApp.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.ImageDto;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.entities.common.ImageEntity;
import org.example.musicApp.store.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Transactional
    public void changeUserImage(MultipartFile imageFile, Principal principal) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFileName(imageFile.getOriginalFilename());
        imageEntity.setFileType(imageFile.getContentType());
        imageEntity.setData(imageFile.getBytes());
        imageEntity.setUser(userService.findByLogin(principal)); // Связываем изображение с пользователем

        imageRepository.save(imageEntity);
    }
}
