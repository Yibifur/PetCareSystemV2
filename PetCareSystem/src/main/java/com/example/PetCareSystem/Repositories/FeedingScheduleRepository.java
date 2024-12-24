package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.FeedingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule,Integer> {
}
