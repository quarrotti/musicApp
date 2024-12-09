package org.example.musicApp.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutAudio.Genre;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.entities.common.ImageEntity;
import org.example.musicApp.store.repositories.AudioRepository;
import org.example.musicApp.store.repositories.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AudioService {
    private final AudioRepository audioRepository;
    private final UserService userService;

    @Transactional
    public List<AudioEntity> listAudioByUser(Principal principal){
        return audioRepository.findAllByUser(userService.findByLogin(principal));
    }
    @Transactional
    public List<AudioEntity> listAudioByUser(Long id){
        return audioRepository.findAllByUser(userService.findById(id));
    }
    public AudioEntity findById(Long id){
        return audioRepository.findById(id).orElse(null);
    }
    public int countOfAudioByEmail(Principal principal){
        return audioRepository.countAudioEntitiesByUser(userService.findByLogin(principal));
    }
    public int countOfAudioById(Long id){
        return audioRepository.countAllByUserId(id);
    }

    public void saveAudio(AudioEntity audio){
        audioRepository.save(audio);
    }

    @Transactional
    public void loadMusic(MultipartFile file, Principal principal, String name, Genre genre) throws IOException {
        System.out.println("запущен метод сервиса");
        AudioEntity audio = new AudioEntity();
        audio.setName(name);
        audio.setFileName(file.getOriginalFilename());
        audio.setFileType(file.getContentType());
        audio.setData(file.getBytes());
        audio.setGenres(Set.of(Genre.valueOf(String.valueOf(genre))));
        audio.setCreator(userService.findByLogin(principal));

        saveAudio(audio);
    }
    @Transactional
    public List<AudioEntity> findTop20ByOrderByCreatedAtDesc(){
        return audioRepository.findTop20ByOrderByCreatedAtDesc(PageRequest.of(0,20));
    }
    @Transactional
    public AudioEntity findByName(String name){
        return audioRepository.findByName(name).orElse(null);
    }

    public void addAudioToUser(Principal principal, Long audioId){// todo check on transactional
        UserEntity user = userService.findByLogin(principal);
        AudioEntity audio = findById(audioId);

        user.getAddedAudios().add(audio);

        userService.save(user);
    }
    @Transactional
    public List<AudioEntity> listOfAddedAudios(Principal principal){
        UserEntity user = userService.findByLogin(principal);

        return user.getAddedAudios();
    }
    @Transactional
    public List<AudioEntity> listOfAddedAudios(Long id){
        UserEntity user = userService.findById(id);

        return user.getAddedAudios();
    }

}
