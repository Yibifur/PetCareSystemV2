package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.GetVetAppointmentDTO;
import com.example.PetCareSystem.DTO.VetAppointmentDTO;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Entities.VetAppointment;
import com.example.PetCareSystem.Enum.UserRole;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Repositories.UserRepository;
import com.example.PetCareSystem.Repositories.VetAppointmentRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public VetAppointmentDTO scheduleAppointment(int vetId, int petId, VetAppointment appointment) {
        // Veteriner kontrolü
        User vet = userRepository.findById(vetId)
                .orElseThrow(() -> new RuntimeException("Veterinarian not found"));

        // Kullanıcının rolünü kontrol et
        if (!vet.getRole().toString().equals("VET")) {
            throw new RuntimeException("The provided user is not a veterinarian");
        }

        // Pet kontrolü
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        // Aynı veteriner için aynı saate randevu kontrolü
        boolean appointmentExists = vetAppointmentRepository.existsByVetIdAndAppointmentDate(vetId, appointment.getAppointmentDate());
        if (appointmentExists) {
            throw new RuntimeException("This veterinarian is already booked for the selected time.");
        }

        // Randevu ID'si set edilmişse (güncelleme için gönderildiyse), önce mevcut randevuyu kontrol et
        if (appointment.getId() != 0) {
            VetAppointment existingAppointment = vetAppointmentRepository.findById(appointment.getId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found"));

            // Mevcut randevu güncelleniyor
            existingAppointment.setVet(vet);
            existingAppointment.setPet(pet);
            existingAppointment.setAppointmentDate(appointment.getAppointmentDate());

            VetAppointment updatedAppointment = vetAppointmentRepository.save(existingAppointment);

            // DTO oluştur ve döndür
            return new VetAppointmentDTO(
                    updatedAppointment.getId(),
                    vet.getUsername(),
                    updatedAppointment.getAppointmentDate()
            );
        }

        // Yeni bir randevu kaydediliyor
        appointment.setVet(vet);
        appointment.setPet(pet);

        VetAppointment savedAppointment = vetAppointmentRepository.save(appointment);

        // DTO oluştur ve döndür
        return new VetAppointmentDTO(
                savedAppointment.getId(),
                vet.getUsername(),
                savedAppointment.getAppointmentDate()
        );
    }



    public List<VetAppointmentDTO> getAppointments(int petId) {
        // Randevuları getir ve DTO'ya dönüştür
        List<VetAppointment> appointments = vetAppointmentRepository.findAllByPetId(petId);
        return appointments.stream()
                .map(app -> new VetAppointmentDTO(app.getId(), app.getVet().getUsername(), app.getAppointmentDate()))
                .collect(Collectors.toList());
    }

    public List<GetVetAppointmentDTO> getAppointmentsByVetId(int vetId) {
        // Veteriner kontrolü
        User vet = userRepository.findById(vetId).orElseThrow(() -> new RuntimeException("Vet not found"));

        // Kullanıcı rolü kontrolü
        if (vet.getRole() != UserRole.VET) {
            throw new RuntimeException("Selected user is not a veterinarian");
        }

        // Veterinerin randevularını getir
        List<VetAppointment> appointments = vetAppointmentRepository.findByVetId(vetId);

        // DTO'ya dönüştür ve döndür
        return appointments.stream()
                .map(appointment -> new GetVetAppointmentDTO(
                        appointment.getId(),
                        appointment.getVet().getUsername(),
                        appointment.getAppointmentDate(),
                        appointment.getPet().getName(),
                        appointment.getPet().getType(),
                        appointment.getPet().getAge(),
                        appointment.getPet().getOwner().getUsername(),
                        appointment.getPet().getOwner().getEmail()
                ))
                .toList();
    }


}
