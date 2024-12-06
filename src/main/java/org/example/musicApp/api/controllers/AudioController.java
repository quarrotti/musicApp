package org.example.musicApp.api.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.services.AudioService;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.example.musicApp.store.entities.aboutAudio.Genre;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AudioController {
    private final AudioService audioService;

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
        return "audio-pages/load-audio";
    }
    @GetMapping("/successful")
    public String sla(){
        return "audio-pages/successful-load";
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
        return "audio-pages/fail-load-audio";
    }

}
