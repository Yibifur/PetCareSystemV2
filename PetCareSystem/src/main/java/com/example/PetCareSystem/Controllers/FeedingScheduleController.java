package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.FeedingScheduleDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.FeedingSchedule;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.FeedingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeding-schedule")
public class FeedingScheduleController {

    @Autowired
    private FeedingScheduleService feedingScheduleService;
    @Autowired
    private PetRepository petRepository;

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<FeedingScheduleDTO> addFeedingSchedule(@PathVariable int petId, @RequestBody FeedingSchedule feedingSchedule, @AuthenticationPrincipal CustomPrincipal principal) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: "));

        // Owner ID ve Principal ID'yi kontrol et
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add medication to this pet.");
        }
        FeedingScheduleDTO savedSchedule = feedingScheduleService.addFeedingSchedule(petId, feedingSchedule);
        return ResponseEntity.ok(savedSchedule);
    }

    @PreAuthorize("hasRole('USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<FeedingScheduleDTO> getFeedingSchedule(@PathVariable int petId, @AuthenticationPrincipal CustomPrincipal principal) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id: "));

        // Owner ID ve Principal ID'yi kontrol et
        if (pet.getOwner().getId()!=(principal.getUserId())) {
            throw new RuntimeException("You are not authorized to add feeding schedule to this pet.");
        }
        FeedingScheduleDTO feedingSchedule = feedingScheduleService.getFeedingScheduleByPetId(petId);
        return ResponseEntity.ok(feedingSchedule);
    }
}
