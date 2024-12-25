package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    List<Medication> findAllByPetsId(int petId);
}
