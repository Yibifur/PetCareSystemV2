package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_medication_pet")
public class MedicationPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    // Getters and Setters
}
