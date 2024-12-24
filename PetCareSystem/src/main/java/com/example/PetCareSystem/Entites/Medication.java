package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String medicationName;

    private Date startDate;

    private Date endDate;

    private String dosage;

    @OneToMany(mappedBy = "medication")
    private List<MedicationPet> pets;

    // Getters and Setters
}
