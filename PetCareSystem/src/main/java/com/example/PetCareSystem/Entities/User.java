package com.example.PetCareSystem.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
    @Table(name = "tb_user")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;

        private String username;

        private String email;

        private String passwordHash;

    @ManyToMany(fetch =FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "Id"))
    private List<Role> roles=new ArrayList<>();

        @OneToMany(mappedBy = "owner")
        private List<Pet> pets;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters and Setters
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

