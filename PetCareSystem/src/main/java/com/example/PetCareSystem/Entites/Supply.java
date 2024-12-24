package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_supply")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String supplyName;

    private String status;

    private int quantity;

    @OneToMany(mappedBy = "supply")
    private List<SupplyPet> pets;

    // Getters and Setters
}
