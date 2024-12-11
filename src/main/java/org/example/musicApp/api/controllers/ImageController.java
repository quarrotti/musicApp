package org.example.musicApp.api.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.services.ImageService;
import org.example.musicApp.api.services.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final UserService userService;
    private final ImageService imageService;

    @Transactional
    @GetMapping("/personal-avatar")
    public ResponseEntity<InputStreamResource> getUserPersonalAvatar(Principal principal) {
        byte[] avatarData = imageService.findByUser(principal).getData();

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
    @Transactional
    @GetMapping("/avatar/{id}")
    public ResponseEntity<InputStreamResource> getUserAvatar(@PathVariable Long id) {
        byte[] avatarData = imageService.findByUserId(id).getData();

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

    @GetMapping("/personalProfile/change-personal-img")
    public String changePersonalImg(){
        return "profile-pages/change-personal-img";
    }

    @PutMapping("/change-personal-img")
    public String changePersonalImg(MultipartFile file, Principal principal){
        try {
            imageService.changeUserImage(file, principal);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo
        }
        return " "; //todo
    }
}
