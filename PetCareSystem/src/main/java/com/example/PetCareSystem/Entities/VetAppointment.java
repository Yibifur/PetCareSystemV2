package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "tb_vet_appointment")
public class VetAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    private User vet;



    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet; // Pet referansı eklendi

    @Column(name = "appointment_date", nullable = false)
    private Date appointmentDate;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getVet() {
        return vet;
    }

    public void setVet(User vet) {
        this.vet = vet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
