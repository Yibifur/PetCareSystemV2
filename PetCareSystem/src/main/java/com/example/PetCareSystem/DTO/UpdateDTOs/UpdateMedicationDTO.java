package com.example.PetCareSystem.DTO.UpdateDTOs;

import java.util.Date;

public class UpdateMedicationDTO {
    private String medicationName;
    private Date startDate;
    private Date endDate;
    private String dosage;

    public UpdateMedicationDTO(String medicationName, Date startDate, Date endDate, String dosage) {
        this.medicationName = medicationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dosage = dosage;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
