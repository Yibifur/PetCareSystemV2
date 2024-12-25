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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<VetAppointment> getVetAppointments() {
        return vetAppointments;
    }

    public void setVetAppointments(List<VetAppointment> vetAppointments) {
        this.vetAppointments = vetAppointments;
    }

    // Getters and Setters
}
