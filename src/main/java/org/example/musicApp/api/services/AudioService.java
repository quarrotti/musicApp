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
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AudioService {
    private final AudioRepository audioRepository;
    private final UserService userService;

    @Transactional
    public List<AudioEntity> listAudioByUser(Principal principal){
        return audioRepository.findAllByUsers(userService.findByLogin(principal).getId());
    }
    @Transactional
    public List<AudioEntity> listAudioByNameAndGenre(String name, String genre) {

        return audioRepository.findAllByNameAndGenres(name, Genre.valueOf(genre));
    }
    @Transactional
    public List<AudioEntity> listAudioByName(String name){
        return audioRepository.findAllByName(name);
    }
    @Transactional
    public List<AudioEntity> listAudioByGenre(String genre){
        return audioRepository.findAllByGenres(Genre.valueOf(genre));
    }

    @Transactional
    public List<AudioEntity> listAudioByUserId(Long id){
        return audioRepository.findAllByUsers(userService.findById(id).getId());
    }
    @Transactional
    public List<AudioEntity> findAll(){
        return audioRepository.findAll();
    }
    @Transactional
    public AudioEntity findById(Long id){
        return audioRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        audioRepository.deleteById(id);
    }


    public Long countOfAudioByEmail(Principal principal){
        return audioRepository.countByUserId(userService.findByLogin(principal).getId());
    }
    public Long countOfAudioById(Long id){
        return audioRepository.countByUserId(id);
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
        audio.setCreator(userService.findByLogin(principal));

        saveAudio(audio);
    }
    @Transactional
    public List<AudioEntity> findTop20ByOrderByCreatedAtDesc(){
        return audioRepository.findTop20ByOrderByCreatedAtDesc(PageRequest.of(0,20));
    }

    public void addAudioToUser(Principal principal, Long audioId) {
        UserEntity user = userService.findByLogin(principal);
        AudioEntity audio = findById(audioId);

        user.getAudios().add(audio);
        audio.getUsers().add(user);

        userService.save(user);
        saveAudio(audio);
    }
}
