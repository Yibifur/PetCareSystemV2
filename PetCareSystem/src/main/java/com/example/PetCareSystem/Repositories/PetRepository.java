package com.example.PetCareSystem.Repositories;


import com.example.PetCareSystem.Entities.Pet;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet,Integer> {
    List<Pet> findAllByOwnerId(int ownerId);
    @Modifying
    @Query(value = "DELETE FROM vaccination_pet WHERE pet_id = :petId", nativeQuery = true)
    void deleteVaccinationRelationships(@Param("petId") int petId);

    @Modifying
    @Query(value = "DELETE FROM supply_pets WHERE pet_id = :petId", nativeQuery = true)
    void deleteSupplyRelationships(@Param("petId") int petId);

    @Modifying
    @Query(value = "DELETE FROM medication_pets WHERE pet_id = :petId", nativeQuery = true)
    void deleteMedicationRelationships(@Param("petId") int petId);

    @Modifying
    @Query(value = "DELETE FROM feedingschedule_pets WHERE pet_id = :petId", nativeQuery = true)
    void deleteFeedingScheduleRelationships(@Param("petId") int petId);





}
