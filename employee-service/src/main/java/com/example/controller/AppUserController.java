package com.example.controller;

import com.example.entity.AppUser;
import com.example.service.AppUserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AppUserController {

    private final AppUserService appUserService;


    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping
    public AppUser createUser(
            @RequestBody AppUser appUser){

        return appUserService.createUser(appUser);
    }

    @GetMapping
    public List<AppUser> findAllUsers(){

        return appUserService.findAllUsers();
    }

    @GetMapping("/{id}")
    public AppUser findUserById(
            @PathVariable Long id){

        return appUserService.findUserById(id);
    }
}
