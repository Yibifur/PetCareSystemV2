package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_feedingschedule")
public class FeedingSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date breakfastTime;

    private Date lunchTime;

    private Date dinnerTime;
    @ManyToMany(fetch =FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "feedingschedule_pets",joinColumns = @JoinColumn(name="feedingschedule_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id",referencedColumnName = "id"))
    private List<Pet> pets=new ArrayList<>();

    public Date getBreakfastTime() {
        return breakfastTime;
    }

    public void setBreakfastTime(Date breakfastTime) {
        this.breakfastTime = breakfastTime;
    }

    public Date getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(Date lunchTime) {
        this.lunchTime = lunchTime;
    }

    public Date getDinnerTime() {
        return dinnerTime;
    }

    public void setDinnerTime(Date dinnerTime) {
        this.dinnerTime = dinnerTime;
    }
    public List<Pet> getPets() {
        return pets;
    }


    // Getters and Setters
}
