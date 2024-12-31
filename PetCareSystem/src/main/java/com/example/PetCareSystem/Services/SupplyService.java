package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.SupplyDTO;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.Supply;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Repositories.SupplyRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
    @Transactional
    public void deleteSupply(int supplyId, int petId) throws BadRequestException {
        // Supply doğrulama işlemi
        Supply supply = supplyRepository.findById(supplyId)
                .orElseThrow(() -> new BadRequestException("Supply not found with ID: " + supplyId));

        // Pet doğrulama işlemi
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new BadRequestException("Pet not found with ID: " + petId));

        // Supply'in ilgili Pet ile ilişkili olup olmadığını kontrol et
        if (!supply.getPets().contains(pet)) {
            throw new RuntimeException("This supply is not associated with the specified pet.");
        }

        // Many-to-Many ilişkiyi sadece Supply tarafında kaldır
        supply.getPets().remove(pet);

        // Güncellenmiş Supply nesnesini kaydet
        supplyRepository.save(supply);

        // Eğer Supply başka bir Pet ile ilişkili değilse, Supply'ı tamamen sil
        if (supply.getPets().isEmpty()) {
            supplyRepository.delete(supply);
        }
    }

}
