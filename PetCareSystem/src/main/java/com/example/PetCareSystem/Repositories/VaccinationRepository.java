package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Integer> {
    List<Vaccination> findAllByPetsId(int petId);
}
