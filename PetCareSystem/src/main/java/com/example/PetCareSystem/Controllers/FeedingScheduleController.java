package com.example.PetCareSystem.Controllers;

import com.example.PetCareSystem.Entities.FeedingSchedule;
import com.example.PetCareSystem.Entities.Medication;
import com.example.PetCareSystem.Repositories.FeedingScheduleRepository;
import com.example.PetCareSystem.Services.FeedingScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/feeding-schedule")
public class FeedingScheduleController {

    private final FeedingScheduleService feedingScheduleService;
    @Autowired
    public FeedingScheduleController(FeedingScheduleService feedingScheduleService) {
        this.feedingScheduleService = feedingScheduleService;
    }


    @GetMapping
    public ResponseEntity<List<FeedingSchedule>> getFeedingSchedules(){
        List<FeedingSchedule> feedingSchedules = feedingScheduleService.getAllFeedingSchedule();
        if (feedingSchedules.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(feedingSchedules);
    }
    @GetMapping("/{feedingSecheduleId}")
    public ResponseEntity<FeedingSchedule> GetFeedingScheduleById(@PathVariable int feedingSecheduleId){
        return feedingScheduleService.getFeedingScheduleById(feedingSecheduleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<FeedingSchedule> CreateFeedingSchedule(@RequestBody FeedingSchedule feedingSchedule){
        FeedingSchedule createdFeedingSchedule = feedingScheduleService.createFeedingSchedule(feedingSchedule);
        return ResponseEntity.status(201).body(createdFeedingSchedule);
    }
    @PutMapping("/{feedingScheduleId}")
    public ResponseEntity<FeedingSchedule> UpdateOneFeedingSchedule(@PathVariable int feedingScheduleId, @RequestBody FeedingSchedule newFeedingSchedule){
        FeedingSchedule updatedFeedingSchedule = feedingScheduleService.updateFeedinggSchedule(feedingScheduleId, newFeedingSchedule);
        if (updatedFeedingSchedule==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedFeedingSchedule);
    }
    @DeleteMapping("/{feedingScheduleId}")
    public ResponseEntity<Void> deleteFeedingSchedule(@PathVariable int feedingScheduleId) {
        feedingScheduleService.deleteFeedingSchedule(feedingScheduleId);
        return ResponseEntity.noContent().build();
    }

}
