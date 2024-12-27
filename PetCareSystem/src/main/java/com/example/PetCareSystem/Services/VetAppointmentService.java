package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.VetAppointmentDTO;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Entities.VetAppointment;
import com.example.PetCareSystem.Enum.UserRole;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Repositories.UserRepository;
import com.example.PetCareSystem.Repositories.VetAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VetAppointmentService {

    @Autowired
    private VetAppointmentRepository vetAppointmentRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    public VetAppointmentDTO scheduleAppointment(int petId, int vetId, VetAppointment appointment) {
        // Pet kontrolü
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));

        // Veteriner kontrolü
        User vet = userRepository.findById(vetId).orElseThrow(() -> new RuntimeException("Vet not found"));

        // Kullanıcı rolü kontrolü
        if (vet.getRole() != UserRole.VET) {
            throw new RuntimeException("Selected user is not a veterinarian");
        }

        // Randevuyu ilişkilendir ve kaydet
        appointment.setPet(pet);
        appointment.setVet(vet);
        VetAppointment savedAppointment = vetAppointmentRepository.save(appointment);

        // DTO oluştur ve döndür
        return new VetAppointmentDTO(savedAppointment.getId(), vet.getUsername(), savedAppointment.getAppointmentDate());
    }

    public List<VetAppointmentDTO> getAppointments(int petId) {
        // Randevuları getir ve DTO'ya dönüştür
        List<VetAppointment> appointments = vetAppointmentRepository.findAllByPetId(petId);
        return appointments.stream()
                .map(app -> new VetAppointmentDTO(app.getId(), app.getVet().getUsername(), app.getAppointmentDate()))
                .collect(Collectors.toList());
    }
}
