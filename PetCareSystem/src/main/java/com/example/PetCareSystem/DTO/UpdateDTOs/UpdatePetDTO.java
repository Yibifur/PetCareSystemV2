package com.example.PetCareSystem.DTO.UpdateDTOs;

import com.example.PetCareSystem.DTO.*;

import java.util.List;

public class UpdatePetDTO {
    private String name;
    private String type;
    private int age;
    private UpdateFeedingScheduleDTO feedingSchedule;
    private List<UpdateMedicationDTO> medications;
    private List<UpdateVaccinationDTO> vaccinations;
    private List<UpdateVetAppointmentDTO> vetAppointments;
    private List<UpdateSupplyDTO> supplies;

    public UpdatePetDTO(String name, String type, int age, UpdateFeedingScheduleDTO feedingSchedule, List<UpdateMedicationDTO> medications, List<UpdateVaccinationDTO> vaccinations, List<UpdateVetAppointmentDTO> vetAppointments, List<UpdateSupplyDTO> supplies) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.feedingSchedule = feedingSchedule;
        this.medications = medications;
        this.vaccinations = vaccinations;
        this.vetAppointments = vetAppointments;
        this.supplies = supplies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public UpdateFeedingScheduleDTO getFeedingSchedule() {
        return feedingSchedule;
    }

    public void setFeedingSchedule(UpdateFeedingScheduleDTO feedingSchedule) {
        this.feedingSchedule = feedingSchedule;
    }

    public List<UpdateMedicationDTO> getMedications() {
        return medications;
    }

    public void setMedications(List<UpdateMedicationDTO> medications) {
        this.medications = medications;
    }

    public List<UpdateVaccinationDTO> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<UpdateVaccinationDTO> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public List<UpdateVetAppointmentDTO> getVetAppointments() {
        return vetAppointments;
    }

    public void setVetAppointments(List<UpdateVetAppointmentDTO> vetAppointments) {
        this.vetAppointments = vetAppointments;
    }

    public List<UpdateSupplyDTO> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<UpdateSupplyDTO> supplies) {
        this.supplies = supplies;
    }
}
