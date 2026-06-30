package com.example.Jwt_Token.controller;

import com.example.Jwt_Token.dto.AuthLogin;
import com.example.Jwt_Token.dto.AuthResponse;
import com.example.Jwt_Token.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/token")
    public AuthResponse generateToken(@RequestBody AuthLogin authLogin){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authLogin.getUsername(),
                        authLogin.getPassword()
                )
        );
        String authToken= jwtUtil.generateToken(authentication.getName());
        return new AuthResponse(authToken);
    }
}
