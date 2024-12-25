package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.FeedingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedingScheduleRepository extends JpaRepository<FeedingSchedule, Integer> {
    Optional<FeedingSchedule> findByPetsId(int petId);
}
