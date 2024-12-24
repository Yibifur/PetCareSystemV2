package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String roleName;



    // Getters and Setters
}
