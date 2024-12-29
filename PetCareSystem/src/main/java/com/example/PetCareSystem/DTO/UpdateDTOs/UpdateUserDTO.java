package com.example.PetCareSystem.DTO.UpdateDTOs;

public class UpdateUserDTO {
    private String username;

    public UpdateUserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
