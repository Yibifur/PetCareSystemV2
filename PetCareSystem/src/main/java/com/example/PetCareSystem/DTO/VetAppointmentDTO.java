package com.example.PetCareSystem.DTO;

import java.util.Date;

public class VetAppointmentDTO {
    private int id;
    private String vetName;
    private Date appointmentDate;

    public VetAppointmentDTO(int id, String vetName, Date appointmentDate) {
        this.id = id;
        this.vetName = vetName;
        this.appointmentDate = appointmentDate;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
