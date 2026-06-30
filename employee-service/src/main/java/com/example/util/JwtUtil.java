package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

//@Component
public class JwtUtil {

    private static final String SECRET_KEY="mysecretkeymysecretkeymysecretkey12345";

    private static final long EXPIRATION_TIME=
    1000*60*60; //1 hour

    public Key getSignKey(){
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes()
        );
    }

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()+EXPIRATION_TIME)
                )
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraxtUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String username,String token){
        String extractedUsername=extraxtUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey)getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
