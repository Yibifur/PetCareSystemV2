package com.example.PetCareSystem.DTO;

import java.util.Date;

public class VaccinationDTO {
    private int id;
    private String vaccinationType;
    private Date vaccinationDate;

    public VaccinationDTO(int id, String vaccinationType, Date vaccinationDate) {
        this.id = id;
        this.vaccinationType = vaccinationType;
        this.vaccinationDate = vaccinationDate;
    }

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
}
