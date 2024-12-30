package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.*;
import com.example.PetCareSystem.DTO.UpdateDTOs.*;
import com.example.PetCareSystem.Entities.*;
import com.example.PetCareSystem.Repositories.*;
import org.apache.coyote.BadRequestException;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    public List<PetDTO> getAllPets() {
        List<Pet> pets = petRepository.findAll();

        return pets.stream()
                .map(pet -> {
                    // Owner bilgisi için UserDTO oluştur
                    User owner = pet.getOwner();
                    UserDTO ownerDTO = owner != null ?
                            new UserDTO(owner.getId(), owner.getUsername(), owner.getEmail(), owner.getRole(), null)
                            : null;

                    // FeedingScheduleDTO oluştur
                    List<FeedingScheduleDTO> feedingSchedules = feedingScheduleRepository.findByPetsId(pet.getId()).stream()
                            .map(schedule -> new FeedingScheduleDTO(schedule.getId(), schedule.getBreakfastTime(), schedule.getLunchTime(), schedule.getDinnerTime()))
                            .collect(Collectors.toList());

                    // MedicationDTO oluştur
                    List<MedicationDTO> medications = medicationRepository.findAllByPetsId(pet.getId()).stream()
                            .map(medication -> new MedicationDTO(medication.getId(), medication.getMedicationName(), medication.getStartDate(), medication.getEndDate(), medication.getDosage()))
                            .collect(Collectors.toList());

                    // VaccinationDTO oluştur
                    List<VaccinationDTO> vaccinations = vaccinationRepository.findAllByPetsId(pet.getId()).stream()
                            .map(vaccination -> new VaccinationDTO(vaccination.getId(), vaccination.getVaccinationType(), vaccination.getVaccinationDate()))
                            .collect(Collectors.toList());

                    // VetAppointmentDTO oluştur
                    List<VetAppointmentDTO> vetAppointments = vetAppointmentRepository.findAllByPetId(pet.getId()).stream()
                            .map(appointment -> new VetAppointmentDTO(appointment.getId(), appointment.getVet().getUsername(), appointment.getAppointmentDate()))
                            .collect(Collectors.toList());

                    // SupplyDTO oluştur
                    List<SupplyDTO> supplies = supplyRepository.findAllByPetsId(pet.getId()).stream()
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
                })
                .collect(Collectors.toList());
    }

    public List<GetPetDTO> getPetsByUserId(int userId) throws BadRequestException {
        // Kullanıcı ID'sine ait tüm petleri al
        List<Pet> pets = petRepository.findAllByOwnerId(userId);

        // Eğer pet bulunamazsa hata fırlat
        if (pets.isEmpty()) {
            throw new BadRequestException("No pets found for user ID: " + userId);
        }

        // Petleri GetPetDTO nesnelerine dönüştür ve döndür
        return pets.stream().map(pet -> new GetPetDTO(
                pet.getId(),
                pet.getName(),
                pet.getType(),
                pet.getAge()
        )).toList();
    }




    public GetPetDTO addPet(int userId, Pet pet) {
        // Kullanıcı kontrolü
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Pet'e kullanıcıyı ekle
        pet.setOwner(user);

        // Pet'i kaydet
        Pet savedPet = petRepository.save(pet);

        // Owner bilgisi için UserDTO oluştur
        UserDTO ownerDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), null);

        // PetDTO oluştur ve döndür
        return new GetPetDTO(
                savedPet.getId(),
                savedPet.getName(),
                savedPet.getType(),
                savedPet.getAge()
        );
    }

    public PetDTO getPetDetails(int petId) {
        // Pet kontrolü
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));

        // Owner bilgisi için UserDTO oluştur
        User owner = pet.getOwner();
        UserDTO ownerDTO = new UserDTO(owner.getId(), owner.getUsername(), owner.getEmail(), owner.getRole(), null);

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
    public UpdatePetDTO updatePet(int petId, UpdatePetDTO petDto) {
        Pet existingPet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));

        // Update pet details
        existingPet.setName(petDto.getName());
        existingPet.setType(petDto.getType());
        existingPet.setAge(petDto.getAge());

        Pet updatedPet = petRepository.save(existingPet);

        // Owner bilgisi için UserDTO oluştur
        User owner = existingPet.getOwner();
        UserDTO ownerDTO = new UserDTO(owner.getId(), owner.getUsername(), owner.getEmail(), owner.getRole(), null);

        // FeedingScheduleDTO oluştur
        List<UpdateFeedingScheduleDTO> feedingSchedules = feedingScheduleRepository.findByPetsId(petId).stream()
                .map(schedule -> new UpdateFeedingScheduleDTO( schedule.getBreakfastTime(), schedule.getLunchTime(), schedule.getDinnerTime()))
                .collect(Collectors.toList());


        // MedicationDTO oluştur
        List<UpdateMedicationDTO> medications = medicationRepository.findAllByPetsId(petId).stream()
                .map(medication -> new UpdateMedicationDTO( medication.getMedicationName(), medication.getStartDate(), medication.getEndDate(), medication.getDosage()))
                .collect(Collectors.toList());

        // VaccinationDTO oluştur
        List<UpdateVaccinationDTO> vaccinations = vaccinationRepository.findAllByPetsId(petId).stream()
                .map(vaccination -> new UpdateVaccinationDTO( vaccination.getVaccinationType(), vaccination.getVaccinationDate()))
                .collect(Collectors.toList());


        // VetAppointmentDTO oluştur
        List<UpdateVetAppointmentDTO> vetAppointments = vetAppointmentRepository.findAllByPetId(petId).stream()
                .map(appointment -> new UpdateVetAppointmentDTO( appointment.getVet().getUsername(), appointment.getAppointmentDate()))
                .collect(Collectors.toList());


        // SupplyDTO oluştur
        List<UpdateSupplyDTO> supplies = supplyRepository.findAllByPetsId(petId).stream()
                .map(supply -> new UpdateSupplyDTO( supply.getSupplyName(), supply.getStatus(), supply.getQuantity()))
                .collect(Collectors.toList());


        return new UpdatePetDTO(

                updatedPet.getName(),
                updatedPet.getType(),
                updatedPet.getAge(),
                feedingSchedules.isEmpty() ? null : feedingSchedules.get(0), // İlk FeedingSchedule
                medications,
                vaccinations,
                vetAppointments,
                supplies
                 // Owner bilgisi
        );
    }
}