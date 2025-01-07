package com.example.PetCareSystem.Security;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
   /* http
                .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırakıyoruz
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/auth/**")
                        //.permitAll()
                        //.requestMatchers("/pets/**")
                        //.authenticated()
                        .anyRequest()
                        //.authenticated()
                        .permitAll()*/
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**")
                        .permitAll()
                        .requestMatchers("/pets/**")
                        .hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/users/{userId}")
                        .hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/users")
                        .hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/users/{userId}/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/feeding-schedule/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/medication/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/supplies/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/vaccination/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/veterinarian-appointments/{vetId}/pets/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/veterinarian-appointments/pets/**")
                        .hasAuthority("ROLE_USER")
                        .requestMatchers("/veterinarian-appointments/{vetId}/appointments")
                        .hasAuthority("ROLE_VET")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}