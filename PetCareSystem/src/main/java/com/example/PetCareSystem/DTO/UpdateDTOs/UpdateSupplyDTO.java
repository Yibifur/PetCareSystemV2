package com.example.PetCareSystem.DTO.UpdateDTOs;

public class UpdateSupplyDTO {
    private String supplyName;
    private String status;
    private int quantity;

    public UpdateSupplyDTO(String supplyName, String status, int quantity) {
        this.supplyName = supplyName;
        this.status = status;
        this.quantity = quantity;
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
