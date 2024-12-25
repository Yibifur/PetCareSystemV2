package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.SupplyDTO;
import com.example.PetCareSystem.Entities.Supply;
import com.example.PetCareSystem.Services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets/{petId}/supplies")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    @PostMapping
    public ResponseEntity<SupplyDTO> addSupply(@PathVariable int petId, @RequestBody Supply supply) {
        SupplyDTO savedSupply = supplyService.addSupply(petId, supply);
        return ResponseEntity.ok(savedSupply);
    }

    @GetMapping
    public ResponseEntity<List<SupplyDTO>> getSupplies(@PathVariable int petId) {
        List<SupplyDTO> supplies = supplyService.getSupplies(petId);
        return ResponseEntity.ok(supplies);
    }
}
