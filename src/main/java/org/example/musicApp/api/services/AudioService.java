package org.example.musicApp.api.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutAudio.Genre;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.example.musicApp.store.repositories.AudioRepository;
import org.springframework.data.domain.PageRequest;
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
//        System.out.println(audioRepository.findAllByUser(userService.findByLogin(principal).getId()));
        return audioRepository.findAllByUser(userService.findByLogin(principal).getId());
    }
    @Transactional
    public List<AudioEntity> listAudioByUser(Long id){
        return audioRepository.findAllByUser(id);
    }
    public AudioEntity findById(Long id){
        return audioRepository.findById(id).orElse(null);
    }
    public int countOfAudioByEmail(Principal principal){
        return audioRepository.countAllByUser(userService.findByLogin(principal).getId());
    }
    public int countOfAudioById(Long id){
        return audioRepository.countAllByUser(id);
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
        audio.setUsers(List.of(userService.findByLogin(principal)));
        audio.setUser(userService.findByLogin(principal));

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

        user.getAudios().add(audio);

        userService.save(user);
    }


}
