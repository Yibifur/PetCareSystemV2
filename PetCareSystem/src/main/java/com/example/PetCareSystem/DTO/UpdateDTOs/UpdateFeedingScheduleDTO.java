package com.example.PetCareSystem.DTO.UpdateDTOs;

import java.util.Date;

public class UpdateFeedingScheduleDTO {
    private Date breakfastTime;
    private Date lunchTime;
    private Date dinnerTime;

    public UpdateFeedingScheduleDTO(Date breakfastTime, Date lunchTime, Date dinnerTime) {
        this.breakfastTime = breakfastTime;
        this.lunchTime = lunchTime;
        this.dinnerTime = dinnerTime;
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
