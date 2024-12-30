package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.DTO.FeedingScheduleDTO;
import com.example.PetCareSystem.Entities.FeedingSchedule;
import com.example.PetCareSystem.Services.FeedingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feeding-schedule")
public class FeedingScheduleController {

    @Autowired
    private FeedingScheduleService feedingScheduleService;
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/pets/{petId}/add")
    public ResponseEntity<FeedingScheduleDTO> addFeedingSchedule(@PathVariable int petId, @RequestBody FeedingSchedule feedingSchedule) {
        FeedingScheduleDTO savedSchedule = feedingScheduleService.addFeedingSchedule(petId, feedingSchedule);
        return ResponseEntity.ok(savedSchedule);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pets/{petId}/get")
    public ResponseEntity<FeedingScheduleDTO> getFeedingSchedule(@PathVariable int petId) {
        FeedingScheduleDTO feedingSchedule = feedingScheduleService.getFeedingScheduleByPetId(petId);
        return ResponseEntity.ok(feedingSchedule);
    }
}
