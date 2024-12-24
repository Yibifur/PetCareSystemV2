package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_feedingschedule")
public class FeedingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date breakfastTime;

    private Date lunchTime;

    private Date dinnerTime;

    @OneToMany(mappedBy = "feedingSchedule")
    private List<FeedingSchedulePet> pets;

    // Getters and Setters
}
