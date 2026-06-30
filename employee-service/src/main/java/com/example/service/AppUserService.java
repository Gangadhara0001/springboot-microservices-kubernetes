package com.example.service;

import com.example.entity.AppUser;
import com.example.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser createUser(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    public List<AppUser> findAllUsers(){
        return appUserRepository.findAll();
    }

    public AppUser findUserById(Long id){

        return appUserRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));
    }
}
