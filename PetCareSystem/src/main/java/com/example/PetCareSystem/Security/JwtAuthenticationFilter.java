package com.example.PetCareSystem.Security;

import com.example.PetCareSystem.Entities.CustomPrincipal;
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

        // Token'dan bilgileri çıkar
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt); // Token'dan username çıkar
        Integer userID = jwtService.extractUserId(jwt); // Token'dan userID çıkar

        // Redis'te oturum geçerliliğini kontrol et
        if (!redisService.isSessionValid(String.valueOf(userID))) {
            System.out.println("JWT token is not valid in Redis session list: " + jwt);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Kullanıcı doğrulama işlemleri
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            if (jwtService.isValidToken(jwt, userDetails)) { // Token geçerliliğini kontrol et
                // Custom Principal oluştur
                CustomPrincipal principal = new CustomPrincipal(userID, username);

                // Authentication nesnesi oluştur ve Context'e ekle
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        principal, // Custom Principal
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("User authenticated: " + username + " with ID: " + userID);
            }
        }
        filterChain.doFilter(request, response);
    }
}


