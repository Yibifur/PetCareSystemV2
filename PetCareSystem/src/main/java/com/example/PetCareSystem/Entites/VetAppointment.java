package com.example.PetCareSystem.Entites;

import com.example.PetCareSystem.Entites.Pet;
import com.example.PetCareSystem.Entites.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_vet_appointment")
public class VetAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    private User vet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet; // Pet referansı eklendi

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    // Getters and Setters
}
