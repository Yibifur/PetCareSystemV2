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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
