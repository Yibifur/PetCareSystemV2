package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.MedicationDTO;
import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.Medication;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PetRepository petRepository;

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<MedicationDTO> addMedication(
            @PathVariable int petId,
            @RequestBody Medication medication,
            @AuthenticationPrincipal CustomPrincipal principal) {

        // Pet'i veritabanından bul
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: "));

        // Owner ID ve Principal ID'yi kontrol et
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }

        // Eşleşme varsa medication ekle
        MedicationDTO savedMedication = medicationService.addMedication(petId, medication);
        return ResponseEntity.ok(savedMedication);
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @GetMapping( "/pets/{petId}/get")
    public ResponseEntity<List<MedicationDTO>> getMedications(@PathVariable int petId, @AuthenticationPrincipal CustomPrincipal principal) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: "));

        // Owner ID ve Principal ID'yi kontrol et
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }
        List<MedicationDTO> medications = medicationService.getMedications(petId);
        return ResponseEntity.ok(medications);
    }
}

