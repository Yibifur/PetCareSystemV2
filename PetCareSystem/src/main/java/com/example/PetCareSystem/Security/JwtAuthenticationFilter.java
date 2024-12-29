package com.example.PetCareSystem.Security;

import com.example.PetCareSystem.Services.CustomUserDetailsService;
import com.example.PetCareSystem.Services.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final RedisService redisService;

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService, RedisService redisService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Authorization header kontrolü
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Token'dan email çıkarma
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt); // extractUsername yerine extractEmail kullanıyoruz
        String userID=String.valueOf(jwtService.extractUserId(jwt));
        if (!redisService.isSessionValid(userID)) {
            System.out.println("JWT token is not valid in Redis session list: " + jwt);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        // Kullanıcı doğrulama işlemleri
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("Authorization Header: " + request.getHeader("Authorization"));
            System.out.println("Extracted email from token: " + username);
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            System.out.println("UserDetails loaded: " + userDetails.getUsername());// Email üzerinden kullanıcıyı yükle
            if (jwtService.isValidToken(jwt, userDetails)) { // Token geçerliliğini kontrol et
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            }
        }
        filterChain.doFilter(request, response);
    }
}

