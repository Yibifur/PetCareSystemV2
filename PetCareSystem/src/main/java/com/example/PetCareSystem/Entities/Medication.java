package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String medicationName;

    private Date startDate;

    private Date endDate;

    private String dosage;

    @ManyToMany(fetch =FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "medication_pets",joinColumns = @JoinColumn(name="medication_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id",referencedColumnName = "id"))
    private List<Pet> pets=new ArrayList<>();

    // Getters and Setters
}
