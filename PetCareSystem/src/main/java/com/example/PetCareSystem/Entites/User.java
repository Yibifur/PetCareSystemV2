package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
    @Table(name = "tb_user")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        private String username;

        private String email;

        private String passwordHash;

        @OneToMany(mappedBy = "user")
        private List<UserRole> roles;

        @OneToMany(mappedBy = "owner")
        private List<Pet> pets;

        // Getters and Setters
    }

