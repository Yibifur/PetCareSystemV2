package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.PetDTO;
import com.example.PetCareSystem.DTO.UserDTO;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    public PetDTO addPet(int userId, Pet pet) {
        // Kullanıcı kontrolü
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Pet'e kullanıcıyı ekle
        pet.setOwner(user);

        // Pet'i kaydet
        Pet savedPet = petRepository.save(pet);

        // Owner bilgisi için UserDTO oluştur
        UserDTO ownerDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), null);

        // PetDTO oluştur ve döndür
        return new PetDTO(
                savedPet.getId(),
                savedPet.getName(),
                savedPet.getType(),
                savedPet.getAge(),
                null, // FeedingScheduleDTO
                null, // List<MedicationDTO>
                null, // List<VaccinationDTO>
                null, // List<VetAppointmentDTO>
                null, // List<SupplyDTO>
                ownerDTO // Owner bilgisi
        );
    }

    public PetDTO getPetDetails(int petId) {
        // Pet kontrolü
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));

        // Owner bilgisi için UserDTO oluştur
        User owner = pet.getOwner();
        UserDTO ownerDTO = new UserDTO(owner.getId(), owner.getUsername(), owner.getEmail(), null);

        // PetDTO oluştur ve döndür
        return new PetDTO(
                pet.getId(),
                pet.getName(),
                pet.getType(),
                pet.getAge(),
                null, // FeedingScheduleDTO
                null, // List<MedicationDTO>
                null, // List<VaccinationDTO>
                null, // List<VetAppointmentDTO>
                null, // List<SupplyDTO>
                ownerDTO // Owner bilgisi
        );
    }

    public void deletePet(int petId) {
        // Pet'i sil
        petRepository.deleteById(petId);
    }
}
