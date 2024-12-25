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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
