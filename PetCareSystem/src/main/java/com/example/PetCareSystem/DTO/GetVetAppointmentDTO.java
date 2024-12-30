package com.example.PetCareSystem.DTO;

import java.util.Date;

public class GetVetAppointmentDTO {
    private int id;
    private String vetName;
    private Date appointmentDate;
    private String petName;
    private String petType;
    private int petAge;
    private String ownerName;
    private String ownerEmail;

    public GetVetAppointmentDTO(int id, String vetName, Date appointmentDate, String petName, String petType, int petAge, String ownerName, String ownerEmail) {
        this.id = id;
        this.vetName = vetName;
        this.appointmentDate = appointmentDate;
        this.petName = petName;
        this.petType = petType;
        this.petAge = petAge;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
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

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
