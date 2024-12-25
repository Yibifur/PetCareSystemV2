package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Integer> {
    List<Supply> findAllByPetsId(int petId);
}
