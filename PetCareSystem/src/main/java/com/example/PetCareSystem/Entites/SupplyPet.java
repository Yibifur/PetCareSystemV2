package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_supply_pet")
public class SupplyPet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "supply_id")
    private Supply supply;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    // Getters and Setters
}