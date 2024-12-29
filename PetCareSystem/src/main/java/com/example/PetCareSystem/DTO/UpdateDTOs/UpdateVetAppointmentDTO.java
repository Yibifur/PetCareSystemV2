package com.example.PetCareSystem.DTO.UpdateDTOs;

import java.util.Date;

public class UpdateVetAppointmentDTO {
    private String vetName;

    public UpdateVetAppointmentDTO(String vetName, Date appointmentDate) {
        this.vetName = vetName;
        this.appointmentDate = appointmentDate;
    }

    private Date appointmentDate;

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }


}
