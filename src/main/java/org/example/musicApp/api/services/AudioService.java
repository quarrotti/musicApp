package org.example.musicApp.api.services;

import lombok.RequiredArgsConstructor;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.repositories.AudioRepository;
import org.example.musicApp.store.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AudioService {
    private final AudioRepository audioRepository;
    private final UserService userService;

    public List<AudioEntity> listAudioByUser(Principal principal){
        return audioRepository.findAllByUser(userService.findByLogin(principal));
    }
    public AudioEntity findById(Long id){
        return audioRepository.findById(id).orElse(null);
    }
}
