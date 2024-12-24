package com.example.PetCareSystem.Repositories;

import com.example.PetCareSystem.Entities.VetAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetAppointmentRepository extends JpaRepository<VetAppointment,Integer> {
}
