package com.example.demo.model;

import jakarta.persistence.*;

@Entity // pour new TABLE en bd
@Table(name = "pc")        // om de la table
public class PC implements TechInterface {

    @Id                                                          // clé primaire NE PAS TOUCHER
    @GeneratedValue(strategy = GenerationType.IDENTITY)          // auto-incrémenté syntax wtf
    private Long id;                                             // clé primaire !ne oas toucher.

    private String brand;
    private int ram;
    private String gpu;

    // constructeur
    public PC() {
    }

    public PC(String brand, int ram, String gpu) {
        this.brand = brand;
        this.ram = ram;
        this.gpu = gpu;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }

    public String getGpu() { return gpu; }
    public void setGpu(String gpu) { this.gpu = gpu; }

    @Override
    public void demarrer() {
        System.out.println("[PC] " + brand + " (" + ram + " Go RAM, GPU : " + gpu + ") s'allume.");
    }
}