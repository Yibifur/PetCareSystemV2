package com.example.PetCareSystem.DTO;

import java.util.Date;

public class FeedingScheduleDTO {
    private int id;
    private Date breakfastTime;
    private Date lunchTime;
    private Date dinnerTime;

    public FeedingScheduleDTO(int id, Date breakfastTime, Date lunchTime, Date dinnerTime) {
        this.id = id;
        this.breakfastTime = breakfastTime;
        this.lunchTime = lunchTime;
        this.dinnerTime = dinnerTime;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
