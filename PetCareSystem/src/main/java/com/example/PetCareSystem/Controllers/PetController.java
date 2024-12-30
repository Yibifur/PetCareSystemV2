package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.DTO.UpdateDTOs.UpdatePetDTO;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping("/{petId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PetDTO> getPetDetails(@PathVariable int petId) {
        PetDTO petDTO = petService.getPetDetails(petId);
        return ResponseEntity.ok(petDTO);
    }
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PetDTO>> getAllPets() {
        List<PetDTO> pets=petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

     @DeleteMapping("/{petId}/delete")
     @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deletePet(@PathVariable int petId) {
        petService.deletePet(petId);
        return ResponseEntity.ok("Pet deleted successfully");
    }

}
