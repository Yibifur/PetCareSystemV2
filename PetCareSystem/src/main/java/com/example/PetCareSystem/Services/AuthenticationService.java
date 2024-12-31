package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Enum.UserRole;
import com.example.PetCareSystem.Repositories.UserRepository;
import com.example.PetCareSystem.Security.JwtService;
import com.example.PetCareSystem.auth.AuthenticationRequest;
import com.example.PetCareSystem.auth.AuthenticationResponse;
import com.example.PetCareSystem.auth.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RedisService redisService;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, RedisService redisService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.redisService = redisService;
    }

    public void register(RegisterRequest request) {
        // Kullanıcıyı oluştur ve şifreyi şifrele
        var user = new User.Builder()
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserRole.valueOf(request.getRole().toUpperCase())) // Varsayılan olarak 'USER' rolü atanır
                .build();
        userRepository.save(user);


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

            // JWT token oluştur
            var jwtToken = jwtService.generateToken(user);
            System.out.println("JWT Token: " + jwtToken);

            // Redis'te session kaydet
            redisService.addSession(String.valueOf(user.getId()), jwtToken);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Authentication error", e);
        }
    }

    public ResponseEntity<String> logout(String token) {
        try {
            // Extract sessionId from token
            String userId = String.valueOf(jwtService.extractUserId(token));

            // Remove session from Redis
            redisService.deleteSession(userId);

            // Return success response
            return ResponseEntity.ok("Logout successful.");
        } catch (Exception e) {
            // Return error response with meaningful message
            String errorMessage = "An error occurred during logout: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}




