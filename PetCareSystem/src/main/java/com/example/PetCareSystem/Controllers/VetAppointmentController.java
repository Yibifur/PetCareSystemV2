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
import org.springframework.http.HttpStatus;
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
    private PetService petService;

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/{vetId}/pets/{petId}/addAppointment")
    public ResponseEntity<?> scheduleAppointment(
            @PathVariable int vetId,
            @PathVariable int petId,
            @RequestBody VetAppointment appointment,
            @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            Pet pet = petService.findById(petId);

            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != (principal.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to add a Veterinarian Appointment to this pet.");
            }

            VetAppointmentDTO savedAppointment = vetAppointmentService.scheduleAppointment(vetId, petId, appointment);
            return ResponseEntity.ok(savedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/getAppointments")
    public ResponseEntity<?> getAppointments(@PathVariable int petId, @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            Pet pet = petService.findById(petId);
            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != (principal.getUserId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No appointments found for pet with ID: " + petId);
            }
            List<VetAppointmentDTO> appointments = vetAppointmentService.getAppointments(petId);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Unable to fetch appointments for pet with ID: " + petId + ". Details: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('VET') and isAuthenticated()")
    @GetMapping("/{vetId}/appointments")
    public ResponseEntity<?> getAppointmentsByVetId(
            @PathVariable int vetId,
            @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            // Oturum açmış kullanıcının ID'si ile vetId'yi karşılaştır
            if (principal.getUserId() != vetId) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to view these appointments.");
            }

            // Servis çağrısı
            List<GetVetAppointmentDTO> appointments = vetAppointmentService.getAppointmentsByVetId(vetId);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


}

