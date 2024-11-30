package org.example.musicApp.api.services;

import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.UserDto;
import org.example.musicApp.store.entities.aboutUser.Role;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.repositories.ImageRepository;
import org.example.musicApp.store.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    public void createUser(UserDto userDto){
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email уже существует");
        }

        UserEntity userForSave = UserEntity.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .username(userDto.getUsername())
                .description(userDto.getDescription())
                .image(userDto.getImage())
                .roles(Set.of(Role.User))
                .build();

        userRepository.save(userForSave);
    }
}
