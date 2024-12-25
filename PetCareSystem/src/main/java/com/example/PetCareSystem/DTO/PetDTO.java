package com.example.PetCareSystem.DTO;

import java.util.List;

public class PetDTO {
    private int id;
    private String name;
    private String type;
    private int age;
    private FeedingScheduleDTO feedingSchedule;
    private List<MedicationDTO> medications;
    private List<VaccinationDTO> vaccinations;
    private List<VetAppointmentDTO> vetAppointments;
    private List<SupplyDTO> supplies;
    private UserDTO owner; // Owner bilgisi

    public PetDTO(int id, String name, String type, int age,
                  FeedingScheduleDTO feedingSchedule,
                  List<MedicationDTO> medications,
                  List<VaccinationDTO> vaccinations,
                  List<VetAppointmentDTO> vetAppointments,
                  List<SupplyDTO> supplies,
                  UserDTO owner) { // Owner parametresi eklendi
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
        this.feedingSchedule = feedingSchedule;
        this.medications = medications;
        this.vaccinations = vaccinations;
        this.vetAppointments = vetAppointments;
        this.supplies = supplies;
        this.owner = owner; // Owner set edildi
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public FeedingScheduleDTO getFeedingSchedule() {
        return feedingSchedule;
    }

    public void setFeedingSchedule(FeedingScheduleDTO feedingSchedule) {
        this.feedingSchedule = feedingSchedule;
    }

    public List<MedicationDTO> getMedications() {
        return medications;
    }

    public void setMedications(List<MedicationDTO> medications) {
        this.medications = medications;
    }

    public List<VaccinationDTO> getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(List<VaccinationDTO> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public List<VetAppointmentDTO> getVetAppointments() {
        return vetAppointments;
    }

    public void setVetAppointments(List<VetAppointmentDTO> vetAppointments) {
        this.vetAppointments = vetAppointments;
    }

    public List<SupplyDTO> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<SupplyDTO> supplies) {
        this.supplies = supplies;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }
}
