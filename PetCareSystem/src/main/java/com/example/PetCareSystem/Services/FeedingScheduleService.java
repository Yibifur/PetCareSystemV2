package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.DTO.FeedingScheduleDTO;
import com.example.PetCareSystem.Entities.FeedingSchedule;
import com.example.PetCareSystem.Entities.Pet;
import com.example.PetCareSystem.Repositories.FeedingScheduleRepository;
import com.example.PetCareSystem.Repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedingScheduleService {

    @Autowired
    private FeedingScheduleRepository feedingScheduleRepository;

    @Autowired
    private PetRepository petRepository;

    public FeedingScheduleDTO addFeedingSchedule(int petId, FeedingSchedule feedingSchedule) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        feedingSchedule.getPets().add(pet);
        FeedingSchedule savedSchedule = feedingScheduleRepository.save(feedingSchedule);

        return new FeedingScheduleDTO(savedSchedule.getId(), savedSchedule.getBreakfastTime(), savedSchedule.getLunchTime(), savedSchedule.getDinnerTime());
    }

    public FeedingScheduleDTO getFeedingScheduleByPetId(int petId) {
        FeedingSchedule feedingSchedule = feedingScheduleRepository.findByPetsId(petId)
                .orElseThrow(() -> new RuntimeException("Feeding schedule not found for petId: " + petId));

        return new FeedingScheduleDTO(
                feedingSchedule.getId(),
                feedingSchedule.getBreakfastTime(),
                feedingSchedule.getLunchTime(),
                feedingSchedule.getDinnerTime()
        );
    }
}
