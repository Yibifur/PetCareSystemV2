package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.MedicationDTO;
import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.Medication;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.MedicationService;
import com.example.PetCareSystem.Services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medication")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;
    @Autowired
    private PetService petService;

    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<?> addMedication(
            @PathVariable int petId,
            @RequestBody Medication medication,
            @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            // Pet'i veritabanından bul
            Pet pet = petService.findById(petId);

            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != (principal.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to add medication to this pet.");
            }

            // Eşleşme varsa medication ekle
            MedicationDTO savedMedication = medicationService.addMedication(petId, medication);
            return ResponseEntity.ok(savedMedication);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<?> getMedications(@PathVariable int petId, @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            // Pet'i veritabanından bul
            Pet pet = petService.findById(petId);

            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != (principal.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to view medications for this pet.");
            }

            // Medication listesi al
            List<MedicationDTO> medications = medicationService.getMedications(petId);
            return ResponseEntity.ok(medications);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}

