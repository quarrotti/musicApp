package org.example.musicApp.api.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.UserDto;
import org.example.musicApp.api.services.AudioService;
import org.example.musicApp.api.services.ImageService;
import org.example.musicApp.api.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final AudioService audioService;
    private final ImageService imageService;
    @GetMapping("/personal-profile")
    @Transactional
    public String personalProfile(Principal principal, Model model){
        model.addAttribute("currentUser", userService.findByLogin(principal));
        model.addAttribute("countAudio", audioService.countOfAudioByEmail(principal));
        return "profile-pages/personal-profile";
    }
    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model){
        model.addAttribute("currentUser", userService.findById(id));
        model.addAttribute("countAudio", audioService.countOfAudioById(id));
        return "profile-pages/profile";
    }

    @GetMapping("/personalProfile/change-personal-img")
    public String changePersonalImg(){
        return "profile-pages/edit-image";
    }
    @PostMapping("/change-personal-img")
    public String changePersonalImg(@RequestParam MultipartFile file, Principal principal){
        try {
            imageService.changeUserImage(file, principal);
            return "redirect:/personal-profile";
        } catch (IOException e) {
            throw new RuntimeException(e); // todo
        }
    }
    @GetMapping("/personalProfile/edit-username")
    public String editUsername(){
        return "profile-pages/edit-username";
    }
    @Transactional
    @PostMapping("/personalProfile/edit-username")
    public String editUsername(@RequestParam(required = false) String username,
                                      Principal principal){
        UserDto user = new UserDto();
        user.setUsername(username);
        userService.editPersonalProfile(user, principal);
        return "redirect:/personal-profile";
    }

    @GetMapping("/users")
    public String allUsers(Model model,Principal principal){
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", userService.findByLogin(principal));
        return "profile-pages/users"; // todo
    }

    @GetMapping("/search-user")
    public String searchUser(@RequestParam String username){
        if(userService.findByUsername(username) != null) {
            return "redirect:/profile/" + userService.findByUsername(username).getId();
        } else return "profile-pages/fail-search";
    }
}
