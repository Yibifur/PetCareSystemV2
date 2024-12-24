package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_vaccination_pet")
public class VaccinationPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vaccination_id")
    private Vaccination vaccination;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    // Getters and Setters
}
