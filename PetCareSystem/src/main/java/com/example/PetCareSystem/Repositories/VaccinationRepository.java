package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinationRepository extends JpaRepository<Vaccination,Integer> {
}
