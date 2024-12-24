package com.example.PetCareSystem.Services;

import com.example.PetCareSystem.Entities.FeedingSchedule;
import com.example.PetCareSystem.Repositories.FeedingScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedingScheduleService {
private final FeedingScheduleRepository feedingScheduleRepository;

    public FeedingScheduleService(FeedingScheduleRepository feedingScheduleRepository) {
        this.feedingScheduleRepository = feedingScheduleRepository;
    }
    public List<FeedingSchedule> getAllFeedingSchedule(){
        return feedingScheduleRepository.findAll();
    }

    public Optional<FeedingSchedule> getFeedingScheduleById(int id){
        return feedingScheduleRepository.findById(id);
    }

    public FeedingSchedule createFeedingSchedule(FeedingSchedule feedingSchedule){
        return feedingScheduleRepository.save(feedingSchedule);
    }
    public FeedingSchedule updateFeedinggSchedule(int id, FeedingSchedule newFeedingSchedule) {
        Optional<FeedingSchedule> feedingSchedule = feedingScheduleRepository.findById(id);
        if (feedingSchedule.isPresent()) {
            FeedingSchedule foundFeedingSchedule = feedingSchedule.get();
            foundFeedingSchedule.setBreakfastTime(newFeedingSchedule.getBreakfastTime());
            foundFeedingSchedule.setLunchTime(newFeedingSchedule.getLunchTime());
            foundFeedingSchedule.setDinnerTime(newFeedingSchedule.getDinnerTime());
            return feedingScheduleRepository.save(foundFeedingSchedule);
        }
        return null;
    }

    public void deleteFeedingSchedule(int id){
        feedingScheduleRepository.deleteById(id);
    }
}
