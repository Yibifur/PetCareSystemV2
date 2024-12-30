package com.example.PetCareSystem.Repositories;


import com.example.PetCareSystem.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Integer> {
    List<Pet> findAllByOwnerId(int ownerId);
}
