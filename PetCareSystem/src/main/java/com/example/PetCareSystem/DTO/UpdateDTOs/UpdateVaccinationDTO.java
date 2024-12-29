package com.example.PetCareSystem.DTO.UpdateDTOs;

import java.util.Date;

public class UpdateVaccinationDTO {
    private String vaccinationType;

    public UpdateVaccinationDTO(String vaccinationType, Date vaccinationDate) {
        this.vaccinationType = vaccinationType;
        this.vaccinationDate = vaccinationDate;
    }

    private Date vaccinationDate;

    public Date getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(Date vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getVaccinationType() {
        return vaccinationType;
    }

    public void setVaccinationType(String vaccinationType) {
        this.vaccinationType = vaccinationType;
    }


}
