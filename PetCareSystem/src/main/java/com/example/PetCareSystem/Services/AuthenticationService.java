package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Enum.UserRole;
import com.example.PetCareSystem.Repositories.UserRepository;
import com.example.PetCareSystem.Security.JwtService;
import com.example.PetCareSystem.auth.AuthenticationRequest;
import com.example.PetCareSystem.auth.AuthenticationResponse;
import com.example.PetCareSystem.auth.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Kullanıcıyı oluştur ve şifreyi şifrele
        var user = new User.Builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserRole.valueOf(request.getRole().toUpperCase())) // Varsayılan olarak 'USER' rolü atanır
                .build();
        userRepository.save(user);

        // JWT token oluştur
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            System.out.println("Authenticating user: " + request.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println("Encoded password in DB: " + user.getPasswordHash());
            System.out.println("Password matches: " + passwordEncoder.matches(request.getPassword(), user.getPasswordHash()));

            var jwtToken = jwtService.generateToken(user);
            System.out.println("JWT Token: " + jwtToken);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Authentication error", e);
        }
    }


}




