package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    // Getters and Setters
}
