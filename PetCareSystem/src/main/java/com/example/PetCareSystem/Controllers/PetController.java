package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.DTO.UpdateDTOs.UpdatePetDTO;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<?> getAllPets() {
        try {
            List<PetDTO> pets = petService.getAllPets();
            return ResponseEntity.ok(pets);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to fetch pet details. " + e.getMessage());
        }
    }

    @DeleteMapping("/{petId}/delete")
    @PreAuthorize("hasRole('ADMIN') and isAuthenticated()")
    public ResponseEntity<?> deletePet(@PathVariable int petId) {
        try {
            petService.deletePet(petId);
            return ResponseEntity.ok("Pet deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Pet not found with ID: " + petId);
        }
    }



}
