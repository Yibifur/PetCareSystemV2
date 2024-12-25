package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.SupplyDTO;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.Supply;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private PetRepository petRepository;

    public SupplyDTO addSupply(int petId, Supply supply) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        supply.getPets().add(pet);
        Supply savedSupply = supplyRepository.save(supply);

        return new SupplyDTO(savedSupply.getId(), savedSupply.getSupplyName(), savedSupply.getStatus(), savedSupply.getQuantity());
    }

    public List<SupplyDTO> getSupplies(int petId) {
        List<Supply> supplies = supplyRepository.findAllByPetsId(petId);
        return supplies.stream()
                .map(supply -> new SupplyDTO(supply.getId(), supply.getSupplyName(), supply.getStatus(), supply.getQuantity()))
                .collect(Collectors.toList());
    }
}
