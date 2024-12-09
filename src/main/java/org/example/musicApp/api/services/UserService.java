package org.example.musicApp.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.UserDto;
import org.example.musicApp.store.entities.aboutUser.Role;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.entities.common.ImageEntity;
import org.example.musicApp.store.repositories.ImageRepository;
import org.example.musicApp.store.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    @Transactional
    public void createUser(UserDto userDto, MultipartFile imageFile) throws IOException{
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email уже существует");
        }

        UserEntity userForSave = UserEntity.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .roles(Set.of(Role.User))
                .build();
        userRepository.save(userForSave);

        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFileName(imageFile.getOriginalFilename());
        imageEntity.setFileType(imageFile.getContentType());
        imageEntity.setData(imageFile.getBytes());
        imageEntity.setUser (userForSave); // Связываем изображение с пользователем

        imageRepository.save(imageEntity);
    }
    @Transactional
    public List<UserEntity> findTop10UsersByAudioCount(){
        return userRepository.findTop10UsersByAudioCount(PageRequest.of(0,10));
    }
    @Transactional
    public UserEntity findByLogin(Principal principal){
        return userRepository.findByEmail(principal.getName()).orElse(null);
    }
    @Transactional
    public UserEntity findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void editPersonalProfile(UserDto user, Principal principal){
        UserEntity updateUser = findByLogin(principal);
        if(user.getUsername() != "") updateUser.setUsername(user.getUsername());
        userRepository.save(updateUser);
    }

    public void save(UserEntity user){
        userRepository.save(user);
    }
}
