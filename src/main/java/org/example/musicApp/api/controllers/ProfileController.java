package org.example.musicApp.api.controllers;

import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.services.AudioService;
import org.example.musicApp.api.services.UserService;
import org.example.musicApp.store.entities.aboutUser.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final AudioService audioService;
    @GetMapping("/personal-profile")
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

    @PostMapping("/users/personalProfile/edit")
    public String editPersonalProfile(@RequestParam(required = false) String username,
                                   @RequestParam(required = false) String email,
                                   @RequestParam(required = false) String description,  Principal principal){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setDescription(description);
        userService.editPersonalProfile(user, principal);
        return "redirect:/personal-profile";
    }
}
