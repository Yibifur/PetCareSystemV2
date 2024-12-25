package com.example.PetCareSystem.DTO;

import java.util.List;

public class UserDTO {
    private int id;
    private String username;
    private String email;
    private List<PetDTO> pets;

    public UserDTO(int id, String username, String email, List<PetDTO> pets) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.pets = pets;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }
}
