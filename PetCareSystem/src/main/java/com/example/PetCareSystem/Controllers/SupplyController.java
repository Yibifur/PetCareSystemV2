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
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;
    @Autowired
    private PetService petService;


    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<SupplyDTO> addSupply(@PathVariable int petId, @RequestBody Supply supply,@AuthenticationPrincipal CustomPrincipal principal) {
        Pet pet = petService.findById(petId);
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }
        SupplyDTO savedSupply = supplyService.addSupply(petId, supply);
        return ResponseEntity.ok(savedSupply);
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<List<SupplyDTO>> getSupplies(@PathVariable int petId,@AuthenticationPrincipal CustomPrincipal principal) {
        Pet pet = petService.findById(petId);
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }
        List<SupplyDTO> supplies = supplyService.getSupplies(petId);
        return ResponseEntity.ok(supplies);
    }
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PutMapping("/pets/{petId}/update/{supplyId}")
    public ResponseEntity<UpdateSupplyDTO> updateSupply(@PathVariable int petId, @PathVariable int supplyId, @RequestBody UpdateSupplyDTO updateSupplyDTO, @AuthenticationPrincipal CustomPrincipal principal) throws BadRequestException {
        Pet pet = petService.findById(petId);
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }

       UpdateSupplyDTO supply= petService.updateSupply(petId,supplyId,updateSupplyDTO);



        return ResponseEntity.ok(supply);

    }
    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @DeleteMapping("/pets/{petId}/delete/{supplyId}")
    public ResponseEntity<String> deleteSupply(@PathVariable int petId,@PathVariable int supplyId,@AuthenticationPrincipal CustomPrincipal principal) throws BadRequestException {
        Pet pet = petService.findById(petId);
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }
        supplyService.deleteSupply(supplyId,petId);
        return ResponseEntity.ok("Pet deleted successfully");
    }
}
