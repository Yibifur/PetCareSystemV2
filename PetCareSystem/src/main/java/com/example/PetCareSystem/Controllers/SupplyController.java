package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.SupplyDTO;
import com.example.PetCareSystem.Entities.Supply;
import com.example.PetCareSystem.Services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<SupplyDTO> addSupply(@PathVariable int petId, @RequestBody Supply supply) {
        SupplyDTO savedSupply = supplyService.addSupply(petId, supply);
        return ResponseEntity.ok(savedSupply);
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<List<SupplyDTO>> getSupplies(@PathVariable int petId) {
        List<SupplyDTO> supplies = supplyService.getSupplies(petId);
        return ResponseEntity.ok(supplies);
    }
}
