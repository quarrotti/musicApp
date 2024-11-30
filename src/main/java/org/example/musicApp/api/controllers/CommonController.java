package org.example.musicApp.api.controllers;

import lombok.RequiredArgsConstructor;
import org.example.musicApp.api.dto.UserDto;
import org.example.musicApp.api.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommonController {
    private final UserService userService;


    @GetMapping("/general")
    public String general(){
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
    public String registryG(){
        return "common-pages/registry&login/registry";
    }

    @PostMapping("/registry")
    public String registryP(UserDto userDto){
        userService.createUser(userDto);
        return "redirect:/login";
    }
}
