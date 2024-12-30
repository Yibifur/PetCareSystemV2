package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.GetVetAppointmentDTO;
import com.example.PetCareSystem.DTO.VetAppointmentDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.VetAppointment;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.VetAppointmentService;
import com.example.PetCareSystem.Services.PetService;
import com.example.PetCareSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veterinarian-appointments")
public class VetAppointmentController {

    @Autowired
    private VetAppointmentService vetAppointmentService;
    @Autowired
    private PetRepository petRepository;

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/{vetId}/pets/{petId}/addAppointment")
    public ResponseEntity<VetAppointmentDTO> scheduleAppointment(
            @PathVariable int vetId,
            @PathVariable int petId,
            @RequestBody VetAppointment appointment,
            @AuthenticationPrincipal CustomPrincipal principal) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: "));

        // Owner ID ve Principal ID'yi kontrol et
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add Veterinarian Appointment to this pet.");
        }
        VetAppointmentDTO savedAppointment = vetAppointmentService.scheduleAppointment(vetId, petId, appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<VetAppointmentDTO>> getAppointments(@PathVariable int petId) {
        List<VetAppointmentDTO> appointments = vetAppointmentService.getAppointments(petId);
        return ResponseEntity.ok(appointments);
    }
    @PreAuthorize("hasRole('VET') and isAuthenticated()")
    @GetMapping("/{vetId}/appointments")
    public ResponseEntity<List<GetVetAppointmentDTO>> getAppointmentsByVetId(
            @PathVariable int vetId,
            @AuthenticationPrincipal CustomPrincipal principal) {
        // Oturum açmış kullanıcının ID'si ile vetId'yi karşılaştır
        if (principal.getUserId() != vetId) {
            throw new RuntimeException("You are not authorized to view these appointments.");
        }

        // Servis çağrısı
        List<GetVetAppointmentDTO> appointments = vetAppointmentService.getAppointmentsByVetId(vetId);
        return ResponseEntity.ok(appointments);
    }

}

