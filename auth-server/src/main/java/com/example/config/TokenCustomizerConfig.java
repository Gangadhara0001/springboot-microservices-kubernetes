package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class TokenCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer(){
        return context ->{
           /* Authentication principal= context.getPrincipal();
            List<String> roles=principal.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            context.getClaims().claims(claims->
                    claims.put("roles",roles));*/
            if ("employee-client".equals(context.getRegisteredClient().getClientId())) {

                context.getClaims().claim(
                        "roles",
                        List.of("ROLE_ADMIN")
                );
            }
        };
    }
}
