package org.example.musicApp.api.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.UserDto;
import org.example.musicApp.api.services.AudioService;
import org.example.musicApp.api.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final UserService userService;
    private final AudioService audioService;

    @Transactional
    @GetMapping("/general")
    public String general(Model model, Principal principal){
        model.addAttribute("currentUser", userService.findByLogin(principal));
        model.addAttribute("users", userService.findTop10UsersByAudioCount());
        model.addAttribute("audios", audioService.listAudioByUser(principal));
        return "common-pages/general-page";
    }

    @GetMapping("/")
    public String helloPage(){
        return "common-pages/hello-page";
    }

    @GetMapping("/login")
    public String loginG(){
        return "common-pages/registry&login/login";
    }

    @GetMapping("/failed-authorization")
    public String failedAuthorization(){
        return "common-pages/registry&login/failed-authorization";
    }

    @GetMapping("/registry")
    public String registryG(Model model,
                            @RequestParam(required = false) String errorMessage,
                            @RequestParam(required = false) String successMessage){
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
        return "common-pages/registry&login/registry";
    }

    @PostMapping("/registry")
    public String registryP(UserDto userDto, MultipartFile file, RedirectAttributes redirectAttributes ){
        try {
            userService.createUser(userDto, file);
            redirectAttributes.addFlashAttribute("successMessage", "Пользователь успешно зарегистрирован");
            return "redirect:/login"; // Перенаправление на страницу входа после успешной регистрации
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка загрузки изображения");
            return "redirect:/registry"; // Вернуться к форме регистрации в случае ошибки
        }

    }
}
