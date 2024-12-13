package org.example.musicApp.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.ImageDto;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.entities.common.ImageEntity;
import org.example.musicApp.store.repositories.ImageRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
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
    public ImageEntity findByUserId(Long id){
        return imageRepository.findByUser(userService.findById(id));
    }
    @Transactional
    public void changeUserImage(MultipartFile imageFile, Principal principal) throws IOException {
        ImageEntity imageEntity = imageRepository.findByUser(userService.findByLogin(principal));
        imageEntity.setFileName(imageFile.getOriginalFilename());
        imageEntity.setFileType(imageFile.getContentType());
        imageEntity.setData(imageFile.getBytes());

        imageRepository.save(imageEntity);
    }
    @Transactional
    public ResponseEntity<InputStreamResource> getUserPersonalAvatar(Principal principal) {
        byte[] avatarData = findByUser(principal).getData();

        if (avatarData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(avatarData));


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=avatar.jpg")
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(avatarData.length)
                .body(resource);
    }
}
