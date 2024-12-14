package org.example.musicApp.api.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.services.AudioService;
import org.example.musicApp.api.services.UserService;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutAudio.Genre;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MusicController {
    private final AudioService audioService;
    private final UserService userService;

    @Transactional
    @GetMapping("/audio/stream/{id}")
    public void streamAudio(@PathVariable Long id, HttpServletResponse response) throws IOException {
        AudioEntity audio = audioService.findById(id);
        response.setContentType("audio/mpeg");
        response.setHeader("Content-Disposition", "inline; filename=\"" + audio.getName() + "\"");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(audio.getData());
        outputStream.flush();
        outputStream.close();
    }
    @GetMapping("/load-audio")
    public String pageForLoadAudio(){
        return "music-pages/load-audio";
    }

    @PostMapping("/load-audio")
    public String loadAudio(@RequestParam MultipartFile file,
                            Principal principal,
                            @RequestParam String name,
                            @RequestParam Genre genre)
    {
        System.out.println("попадание метод контроллера");
        try {
            audioService.loadMusic(file, principal, name, genre);
            System.out.println("try пройден");
            return "redirect:/general";
        } catch (IOException e) {
            System.out.println("catch пройден");
            return "redirect:/fail-load-audio";
        }
    }

    @GetMapping("/fail-load-audio")
    public String failLoadAudio(){
        return "music-pages/fail-load-audio";
    }

    @GetMapping("/music")
    public String generalMusicPage(Model model, Principal principal){
        model.addAttribute("lastAddedMusic", audioService.findTop20ByOrderByCreatedAtDesc());
        model.addAttribute("currentUser", userService.findByLogin(principal));
        return "music-pages/general-music-page";
    }

    @GetMapping("/search-audio")
    public String searchMusic(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String genre,
                              Model model,
                              Principal principal){

        if(name != "" && genre != "") {
            model.addAttribute("currentAudios", audioService.listAudioByNameAndGenre(name, genre));
        } else if (name != "" && genre == "") {
            model.addAttribute("currentAudios", audioService.listAudioByName(name));
        } else if (name == "" && genre != ""){
            model.addAttribute("currentAudios", audioService.listAudioByGenre(genre));
        } else model.addAttribute("currentAudios", audioService.findAll());
        model.addAttribute("currentUser", userService.findByLogin(principal));
        return "music-pages/current-audios";
    }

    @GetMapping("/personal-music")
    public String personalMusic(Principal principal, Model model){
        model.addAttribute("listOfAudio", audioService.listAudioByUser(principal));
        return "music-pages/personal-music";
    }

    @GetMapping("/user-music/{id}")
    public String userMusic(@PathVariable Long id, Model model){
        model.addAttribute("listOfAudio", audioService.listAudioByUserId(id));
        return "music-pages/user-music";
    }

    @PostMapping("/add-music/{id}")
    public String addMusic(Principal principal, @PathVariable Long id){
        audioService.addAudioToUser(principal, id);
        return "redirect:/music";
    }

    @PostMapping("/delete-audio/{id}")
    public String deleteAudio(@PathVariable Long id, Principal principal){
        if(audioService.findById(id).getCreator().equals(userService.findByLogin(principal))){
            audioService.deleteById(id);
            return "redirect:/personal-music";
        } else return "music-pages/fail-delete";
    }

}
