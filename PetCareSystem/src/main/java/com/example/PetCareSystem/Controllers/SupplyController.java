package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.DTO.SupplyDTO;
import com.example.PetCareSystem.DTO.UpdateDTOs.UpdatePetDTO;
import com.example.PetCareSystem.DTO.UpdateDTOs.UpdateSupplyDTO;
import com.example.PetCareSystem.DTO.UserDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.Supply;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.PetService;
import com.example.PetCareSystem.Services.SupplyService;
import com.example.PetCareSystem.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;
    @Autowired
    private PetService petService;


    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<?> addSupply(@PathVariable int petId, @RequestBody Supply supply, @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            Pet pet = petService.findById(petId);
            if (pet.getOwner().getId() != (principal.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to add supplies to this pet.");
            }
            SupplyDTO savedSupply = supplyService.addSupply(petId, supply);
            return ResponseEntity.ok(savedSupply);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<?> getSupplies(@PathVariable int petId, @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            Pet pet = petService.findById(petId);
            if (pet.getOwner().getId() != (principal.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to view supplies for this pet.");
            }
            List<SupplyDTO> supplies = supplyService.getSupplies(petId);
            return ResponseEntity.ok(supplies);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @PutMapping("/pets/{petId}/update/{supplyId}")
    public ResponseEntity<?> updateSupply(
            @PathVariable int petId,
            @PathVariable int supplyId,
            @RequestBody UpdateSupplyDTO updateSupplyDTO,
            @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            // Pet doğrulama
            Pet pet = petService.findById(petId);
            if (pet.getOwner().getId() != principal.getUserId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of(
                                "error", "Forbidden",
                                "message", "You are not authorized to update supplies for this pet."
                        ));
            }

            // Supply güncelleme işlemi
            UpdateSupplyDTO updatedSupply = petService.updateSupply(petId, supplyId, updateSupplyDTO);
            return ResponseEntity.ok(Map.of(
                    "message", "Supply updated successfully",
                    "data", updatedSupply
            ));
        } catch (EntityNotFoundException e) {
            // Supply veya Pet bulunamadığında
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not Found",
                    "message", e.getMessage()
            ));
        } catch (IllegalArgumentException e) {
            // Yanlış veri gönderildiğinde
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "Bad Request",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            // Genel hata durumları
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Internal Server Error",
                    "message", e.getMessage()
            ));
        }
    }


    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @DeleteMapping("/pets/{petId}/delete/{supplyId}")
    public ResponseEntity<?> deleteSupply(
            @PathVariable int petId,
            @PathVariable int supplyId,
            @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            // Pet doğrulama
            Pet pet = petService.findById(petId);
            if (pet.getOwner().getId() != principal.getUserId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of(
                                "error", "Forbidden",
                                "message", "You are not authorized to delete supplies for this pet."
                        ));
            }

            // Supply silme işlemi
            supplyService.deleteSupply(supplyId, petId);
            return ResponseEntity.ok(Map.of(
                    "message", "Supply deleted successfully"
            ));
        } catch (EntityNotFoundException e) {
            // Supply veya Pet bulunamadığında
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "Not Found",
                    "message", e.getMessage()
            ));
        } catch (AccessDeniedException e) {
            // Supply belirtilen Pet ile ilişkilendirilmemişse
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                    "error", "Access Denied",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            // Genel hata durumları
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Internal Server Error",
                    "message", e.getMessage()
            ));
        }
    }

}



