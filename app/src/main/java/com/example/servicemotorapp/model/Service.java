package com.example.servicemotorapp.model;

import java.io.Serializable;

public class Service implements Serializable {
    // Daftar atribut, field (table database = kolom)
    String namaMontir;
    char category;
    String fullCategory;
    int slotService;
    int sisaSlotService;

    public Service(String namaMontir, char category, String fullCategory, int slotService, int sisaSlotService) {
        this.namaMontir = namaMontir;
        this.category = category;
        this.fullCategory = fullCategory;
        this.slotService = slotService;
        this.sisaSlotService = sisaSlotService;
    }

    public String getNamaMontir() {
        return namaMontir;
    }

    public void setNamaMontir(String namaMontir) {
        this.namaMontir = namaMontir;
    }

    public char getCategory() {
        return category;
    }

    public void setCategory(char category) {
        this.category = category;
    }

    public String getFullCategory() {
        return fullCategory;
    }

    public void setFullCategory(String fullCategory) {
        this.fullCategory = fullCategory;
    }

    public int getSlotService() {
        return slotService;
    }

    public void setSlotService(int slotService) {
        this.slotService = slotService;
    }

    public int getSisaSlotService() {
        return sisaSlotService;
    }

    public void setSisaSlotService(int sisaSlotService) {
        this.sisaSlotService = sisaSlotService;
    }
}
