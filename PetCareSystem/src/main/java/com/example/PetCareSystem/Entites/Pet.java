package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String type;

    private int age;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "pet")
    private List<VaccinationPet> vaccinations;

    @OneToMany(mappedBy = "pet")
    private List<FeedingSchedulePet> feedingSchedules;

    @OneToMany(mappedBy = "pet")
    private List<MedicationPet> medications;

    @OneToMany(mappedBy = "pet")
    private List<SupplyPet> supplies;

    @OneToMany(mappedBy = "pet")
    private List<VetAppointment> vetAppointments;

    // Getters and Setters
}
