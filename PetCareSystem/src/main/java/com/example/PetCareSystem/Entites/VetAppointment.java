package com.example.PetCareSystem.Entites;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_vet_appointment")
public class VetAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "vet_id", nullable = false)
    private int vetId; // Veterinerin ID'si

    @Column(name = "user_id", nullable = false)
    private int userId; // Kullanıcının ID'si

    @Column(name = "pet_id", nullable = false)
    private int petId; // Hayvanın ID'si

    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate; // Randevu tarihi

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVetId() {
        return vetId;
    }

    public void setVetId(int vetId) {
        this.vetId = vetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}

