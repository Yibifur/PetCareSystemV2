package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.*;
import com.example.PetCareSystem.Entities.*;
import com.example.PetCareSystem.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Autowired
    private VaccinationRepository vaccinationRepository;

    @Autowired
    private VetAppointmentRepository vetAppointmentRepository;

    @Autowired
    private SupplyRepository supplyRepository;

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

        // FeedingScheduleDTO oluştur
        List<FeedingScheduleDTO> feedingSchedules = feedingScheduleRepository.findByPetsId(petId).stream()
                .map(schedule -> new FeedingScheduleDTO(schedule.getId(), schedule.getBreakfastTime(), schedule.getLunchTime(), schedule.getDinnerTime()))
                .collect(Collectors.toList());

        // MedicationDTO oluştur
        List<MedicationDTO> medications = medicationRepository.findAllByPetsId(petId).stream()
                .map(medication -> new MedicationDTO(medication.getId(), medication.getMedicationName(), medication.getStartDate(), medication.getEndDate(), medication.getDosage()))
                .collect(Collectors.toList());

        // VaccinationDTO oluştur
        List<VaccinationDTO> vaccinations = vaccinationRepository.findAllByPetsId(petId).stream()
                .map(vaccination -> new VaccinationDTO(vaccination.getId(), vaccination.getVaccinationType(), vaccination.getVaccinationDate()))
                .collect(Collectors.toList());

        // VetAppointmentDTO oluştur
        List<VetAppointmentDTO> vetAppointments = vetAppointmentRepository.findAllByPetId(petId).stream()
                .map(appointment -> new VetAppointmentDTO(appointment.getId(), appointment.getVet().getUsername(), appointment.getAppointmentDate()))
                .collect(Collectors.toList());

        // SupplyDTO oluştur
        List<SupplyDTO> supplies = supplyRepository.findAllByPetsId(petId).stream()
                .map(supply -> new SupplyDTO(supply.getId(), supply.getSupplyName(), supply.getStatus(), supply.getQuantity()))
                .collect(Collectors.toList());

        // PetDTO oluştur ve döndür
        return new PetDTO(
                pet.getId(),
                pet.getName(),
                pet.getType(),
                pet.getAge(),
                feedingSchedules.isEmpty() ? null : feedingSchedules.get(0), // İlk FeedingSchedule
                medications,
                vaccinations,
                vetAppointments,
                supplies,
                ownerDTO // Owner bilgisi
        );
    }

    public void deletePet(int petId) {
        // Pet'i sil
        petRepository.deleteById(petId);
    }
}