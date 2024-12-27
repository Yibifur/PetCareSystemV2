package com.example.PetCareSystem.DTO;

import com.example.PetCareSystem.Enum.UserRole;
import java.util.List;

public class UserDTO {
    private int id;
    private String username;
    private String email;
    private UserRole role; // Kullanıcı rolü
    private List<PetDTO> pets;

    public UserDTO(int id, String username, String email, UserRole role, List<PetDTO> pets) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<PetDTO> getPets() {
        return pets;
    }

    public void setPets(List<PetDTO> pets) {
        this.pets = pets;
    }
}
