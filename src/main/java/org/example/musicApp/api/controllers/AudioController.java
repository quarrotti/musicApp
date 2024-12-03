package org.example.musicApp.api.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.services.AudioService;
import org.example.musicApp.store.entities.aboutAudio.AudioEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

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
}
