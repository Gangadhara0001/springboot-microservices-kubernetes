package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.UUID;

@Configuration
public class RegisteredClientConfig {
    @Bean
    public InMemoryRegisteredClientRepository inMemoryRegisteredClientRepository(
            PasswordEncoder encoder){
        RegisteredClient registeredClient= RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("employee-client")
                .clientSecret(encoder.encode("secret123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(
                        AuthorizationGrantType.AUTHORIZATION_CODE
                )

                .authorizationGrantType(
                        AuthorizationGrantType.REFRESH_TOKEN
                )
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri(
                        "https://oauth.pstmn.io/v1/callback"
                )
                .scope("openid")
                .scope("profile")
                .scope("read")
                .scope("write")
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }


}
