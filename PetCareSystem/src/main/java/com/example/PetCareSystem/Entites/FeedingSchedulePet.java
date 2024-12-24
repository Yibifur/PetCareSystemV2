package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_feedingschedule_pet")
public class FeedingSchedulePet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "feedingschedule_id")
    private FeedingSchedule feedingSchedule;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    // Getters and Setters
}
