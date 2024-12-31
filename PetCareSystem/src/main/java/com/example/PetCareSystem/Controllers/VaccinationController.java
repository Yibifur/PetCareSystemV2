package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.VaccinationDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.Vaccination;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.PetService;
import com.example.PetCareSystem.Services.VaccinationService;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/vaccination")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;
    @Autowired
    private PetService petService;

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<?> addVaccination(
            @PathVariable int petId,
            @RequestBody Vaccination vaccination,
            @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            // Pet doğrulama
            Pet pet = petService.findById(petId);

            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != principal.getUserId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to add vaccination to this pet.");
            }

            // Aşıyı ekle
            VaccinationDTO savedVaccination = vaccinationService.addVaccination(petId, vaccination);
            return ResponseEntity.ok(savedVaccination);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<?> getVaccinations(
            @PathVariable int petId,
            @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            // Pet doğrulama
            Pet pet = petService.findById(petId);

            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != principal.getUserId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to view vaccinations for this pet.");
            }

            // Aşıları getir
            List<VaccinationDTO> vaccinations = vaccinationService.getVaccinations(petId);
            return ResponseEntity.ok(vaccinations);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


}

