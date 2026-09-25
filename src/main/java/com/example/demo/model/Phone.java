package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "phone")
public class Phone implements TechInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private int ram;
    private String network;

    public Phone() {
    }

    public Phone(String brand, int ram, String network) {
        this.brand = brand;
        this.ram = ram;
        this.network = network;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    @Override
    public void demarrer() {
        System.out.println("[Phone] " + brand + " s'allume et se connecte au reseau " + network + ".");
    }
}
