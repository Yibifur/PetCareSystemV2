package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.MedicationDTO;
import com.example.PetCareSystem.Entities.Medication;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Repositories.MedicationRepository;
import com.example.PetCareSystem.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private PetRepository petRepository;

    public MedicationDTO addMedication(int petId, Medication medication) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        medication.getPets().add(pet);
        Medication savedMedication = medicationRepository.save(medication);
        return new MedicationDTO(savedMedication.getId(), savedMedication.getMedicationName(), savedMedication.getStartDate(), savedMedication.getEndDate(), savedMedication.getDosage());
    }

    public List<MedicationDTO> getMedications(int petId) {
        List<Medication> medications = medicationRepository.findAllByPetsId(petId);
        return medications.stream()
                .map(med -> new MedicationDTO(med.getId(), med.getMedicationName(), med.getStartDate(), med.getEndDate(), med.getDosage()))
                .collect(Collectors.toList());
    }
}
