package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.FeedingScheduleDTO;
import com.example.PetCareSystem.Entities.CustomPrincipal;
import com.example.PetCareSystem.Entities.FeedingSchedule;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Repositories.PetRepository;
import com.example.PetCareSystem.Services.FeedingScheduleService;
import com.example.PetCareSystem.Services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private PetService petService;

    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<?> addFeedingSchedule(@PathVariable int petId, @RequestBody FeedingSchedule feedingSchedule, @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            Pet pet = petService.findById(petId);

            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != principal.getUserId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to add feeding schedule to this pet.");
            }

            FeedingScheduleDTO savedSchedule = feedingScheduleService.addFeedingSchedule(petId, feedingSchedule);
            return ResponseEntity.ok(savedSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER') and isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<?> getFeedingSchedule(@PathVariable int petId, @AuthenticationPrincipal CustomPrincipal principal) {
        try {
            Pet pet = petService.findById(petId);

            // Owner ID ve Principal ID'yi kontrol et
            if (pet.getOwner().getId() != principal.getUserId()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You are not authorized to view the feeding schedule of this pet.");
            }

            FeedingScheduleDTO feedingSchedule = feedingScheduleService.getFeedingScheduleByPetId(petId);
            return ResponseEntity.ok(feedingSchedule);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
