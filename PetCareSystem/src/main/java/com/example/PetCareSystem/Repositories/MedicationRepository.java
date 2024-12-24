package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication,Integer> {
}
