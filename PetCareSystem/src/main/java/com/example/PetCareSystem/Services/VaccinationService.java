package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.VaccinationDTO;
import com.example.PetCareSystem.Entities.Vaccination;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Repositories.VaccinationRepository;
import com.example.PetCareSystem.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private PetRepository petRepository;

    public VaccinationDTO addVaccination(int petId, Vaccination vaccination) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        vaccination.getPets().add(pet);
        Vaccination savedVaccination = vaccinationRepository.save(vaccination);
        return new VaccinationDTO(savedVaccination.getId(), savedVaccination.getVaccinationType(), savedVaccination.getVaccinationDate());
    }

    public List<VaccinationDTO> getVaccinations(int petId) {
        List<Vaccination> vaccinations = vaccinationRepository.findAllByPetsId(petId);
        return vaccinations.stream()
                .map(vac -> new VaccinationDTO(vac.getId(), vac.getVaccinationType(), vac.getVaccinationDate()))
                .collect(Collectors.toList());
    }
}
