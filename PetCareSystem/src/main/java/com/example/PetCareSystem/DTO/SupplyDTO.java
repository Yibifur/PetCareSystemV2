package com.example.PetCareSystem.DTO;

public class SupplyDTO {
    private int id;
    private String supplyName;
    private String status;
    private int quantity;

    public SupplyDTO(int id, String supplyName, String status, int quantity) {
        this.id = id;
        this.supplyName = supplyName;
        this.status = status;
        this.quantity = quantity;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
