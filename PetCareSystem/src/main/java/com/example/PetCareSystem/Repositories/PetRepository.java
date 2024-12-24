package com.example.PetCareSystem.Repositories;


import com.example.PetCareSystem.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Integer> {
}
