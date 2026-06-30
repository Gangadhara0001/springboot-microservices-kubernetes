package com.example.service;

import com.example.entity.AppUser;
import com.example.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public CustomUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @java.lang.Override
    public UserDetails loadUserByUsername(java.lang.String username) throws UsernameNotFoundException {
        AppUser appUser=appUserRepository
                .findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("user not found"));

        return new User(
                appUser.getUsername(),
                appUser.getPassword(),
                List.of(
                        new SimpleGrantedAuthority(
                                appUser.getRole()
                        )
                )
        );
    }
}
