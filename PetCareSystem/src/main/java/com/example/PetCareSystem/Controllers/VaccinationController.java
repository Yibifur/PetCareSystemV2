package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.VaccinationDTO;
import com.example.PetCareSystem.Entities.Vaccination;
import com.example.PetCareSystem.Services.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets/{petId}/vaccination")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @PostMapping
    public ResponseEntity<VaccinationDTO> addVaccination(@PathVariable int petId, @RequestBody Vaccination vaccination) {
        VaccinationDTO savedVaccination = vaccinationService.addVaccination(petId, vaccination);
        return ResponseEntity.ok(savedVaccination);
    }

    @GetMapping
    public ResponseEntity<List<VaccinationDTO>> getVaccinations(@PathVariable int petId) {
        List<VaccinationDTO> vaccinations = vaccinationService.getVaccinations(petId);
        return ResponseEntity.ok(vaccinations);
    }
}

