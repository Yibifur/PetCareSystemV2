package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_vaccination")
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String vaccinationType;

    private Date vaccinationDate;

    @OneToMany(mappedBy = "vaccination")
    private List<VaccinationPet> pets;

    // Getters and Setters
}
