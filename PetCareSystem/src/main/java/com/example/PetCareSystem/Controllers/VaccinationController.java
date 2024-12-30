package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.VaccinationDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.Vaccination;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.VaccinationService;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vaccination")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;
    @Autowired
    private PetRepository petRepository;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<VaccinationDTO> addVaccination(@PathVariable int petId, @RequestBody Vaccination vaccination, @AuthenticationPrincipal CustomPrincipal principal) {
        // Pet'i veritabanından bul
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: "));

        // Owner ID ve Principal ID'yi kontrol et
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }
        VaccinationDTO savedVaccination = vaccinationService.addVaccination(petId, vaccination);
        return ResponseEntity.ok(savedVaccination);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<List<VaccinationDTO>> getVaccinations(@PathVariable int petId, @AuthenticationPrincipal CustomPrincipal principal) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: "));

        // Owner ID ve Principal ID'yi kontrol et
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }
        List<VaccinationDTO> vaccinations = vaccinationService.getVaccinations(petId);
        return ResponseEntity.ok(vaccinations);
    }
}

