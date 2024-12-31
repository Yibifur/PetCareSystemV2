package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.Services.AuthenticationService;
import com.example.PetCareSystem.auth.AuthenticationRequest;
import com.example.PetCareSystem.auth.AuthenticationResponse;
import com.example.PetCareSystem.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/users")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ResponseEntity.ok("Registration successful");
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        // Authorization header'dan token'ı çıkar
        String token = authHeader.substring(7); // "Bearer " kısmını çıkar

        // Logout işlemini gerçekleştir
        authenticationService.logout(token);

        // Yanıt döndür
        return ResponseEntity.ok("Logout successful");
    }


}
