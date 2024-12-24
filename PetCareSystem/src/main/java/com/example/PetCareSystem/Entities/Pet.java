package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String type;

    private int age;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "pet")
    private List<VetAppointment> vetAppointments;

    // Getters and Setters
}
