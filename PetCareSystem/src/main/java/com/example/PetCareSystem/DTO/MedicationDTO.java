package com.example.PetCareSystem.DTO;

import java.util.Date;

public class MedicationDTO {
    private int id;
    private String medicationName;
    private Date startDate;
    private Date endDate;
    private String dosage;

    public MedicationDTO(int id, String medicationName, Date startDate, Date endDate, String dosage) {
        this.id = id;
        this.medicationName = medicationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
}
