package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_supply")
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String supplyName;

    private String status;

    private int quantity;

    @ManyToMany(fetch =FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "supply_pets",joinColumns = @JoinColumn(name="supply_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id",referencedColumnName = "id"))
    private List<Pet> pets=new ArrayList<>();

    // Getters and Setters
}
