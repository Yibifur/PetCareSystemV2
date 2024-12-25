package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_vaccination")
public class Vaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String vaccinationType;

    private Date vaccinationDate;

    @ManyToMany(fetch =FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "vaccination_pet",joinColumns = @JoinColumn(name="vaccination_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id",referencedColumnName = "id"))
    private List<Pet> pets=new ArrayList<>();

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(String vaccinationType) {
        this.vaccinationType = vaccinationType;
    }

    public Date getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Date vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
