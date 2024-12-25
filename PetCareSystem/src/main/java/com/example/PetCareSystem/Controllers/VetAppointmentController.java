package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.VetAppointmentDTO;
import com.example.PetCareSystem.Entities.VetAppointment;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Entities.User;
import com.example.PetCareSystem.Services.VetAppointmentService;
import com.example.PetCareSystem.Services.PetService;
import com.example.PetCareSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets/{petId}/veterinarian-appointments")
public class VetAppointmentController {

    @Autowired
    private VetAppointmentService vetAppointmentService;

    @PostMapping
    public ResponseEntity<VetAppointmentDTO> scheduleAppointment(
            @PathVariable int petId,
            @RequestParam int vetId,
            @RequestBody VetAppointment appointment) {
        VetAppointmentDTO savedAppointment = vetAppointmentService.scheduleAppointment(petId, vetId, appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @GetMapping
    public ResponseEntity<List<VetAppointmentDTO>> getAppointments(@PathVariable int petId) {
        List<VetAppointmentDTO> appointments = vetAppointmentService.getAppointments(petId);
        return ResponseEntity.ok(appointments);
    }
}

