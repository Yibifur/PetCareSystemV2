package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.MedicationDTO;
import com.example.PetCareSystem.Entities.Medication;
import com.example.PetCareSystem.Services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets/{petId}/medication")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationDTO> addMedication(@PathVariable int petId, @RequestBody Medication medication) {
        MedicationDTO savedMedication = medicationService.addMedication(petId, medication);
        return ResponseEntity.ok(savedMedication);
    }

    @GetMapping
    public ResponseEntity<List<MedicationDTO>> getMedications(@PathVariable int petId) {
        List<MedicationDTO> medications = medicationService.getMedications(petId);
        return ResponseEntity.ok(medications);
    }
}

