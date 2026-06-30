package com.example.config;

import com.example.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

    /*private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }*/

    //without method level security
    /*@Bean
    SecurityFilterChain defaultsecurityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/public/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/employee/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/employee/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/employee/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/employee/**").hasRole("ADMIN")

                        .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }*/

    /*@Bean
    SecurityFilterChain defaultsecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf((csrf) -> csrf.disable())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**")
                        .permitAll()

                        .requestMatchers("/api/employee/**")
                        .authenticated()

                        .requestMatchers("/api/user/**")
                        .authenticated()

                        .requestMatchers("/api/auth/**").permitAll()

                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);
               //.httpBasic(Customizer.withDefaults());
                return httpSecurity.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/info",
                                "/actuator/prometheus"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/employee/**")
                      //without role based configuration  .hasAuthority("SCOPE_read")
                        .hasAnyRole("USER","ADMIN") // with role based

                        .requestMatchers(HttpMethod.POST,
                                "/api/employee/**")
                    //    .hasAuthority("SCOPE_write")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/api/employee/**")
                        //.hasAuthority("SCOPE_write")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/api/employee/**")
                        //.hasAuthority("SCOPE_write")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .oauth2ResourceServer(oauth2->oauth2
                //thi is for default        .jwt(Customizer.withDefaults()));
                        .jwt(jwt ->
                                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return httpSecurity.build();

    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter authoritiesConverter =
                new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthorityPrefix("SCOPE_");
        authoritiesConverter.setAuthoritiesClaimName("scope");

        JwtAuthenticationConverter converter =
                new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt-> {
            List<GrantedAuthority> authorities=
                    new ArrayList<>(authoritiesConverter.convert(jwt));
            List<String> roles=jwt.getClaimAsStringList("roles");
            System.out.println("JWT Roles: " + roles);
            if (roles != null) {
                roles.forEach(role ->
                        authorities.add(
                                new SimpleGrantedAuthority(role)
                        ));
            }
            System.out.println("Authorities: " + authorities);
            return authorities;

                }


        );

        return converter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Commented below part because we are using database
    /*@Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){
        UserDetails user= User.builder()
            .username("user")
            .password(encoder.encode("12345"))
            .roles("USER")
            .build();
        UserDetails admin= User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user,admin);

    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
