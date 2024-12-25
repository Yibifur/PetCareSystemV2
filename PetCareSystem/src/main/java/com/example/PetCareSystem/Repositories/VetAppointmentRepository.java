package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.VetAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetAppointmentRepository extends JpaRepository<VetAppointment, Integer> {
    List<VetAppointment> findAllByPetId(int petId);
}
