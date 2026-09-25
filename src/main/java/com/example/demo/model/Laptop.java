package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "laptop")
public class Laptop implements TechInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private int ram;
    private int batteryLife;

    public Laptop() {
    }

    public Laptop(String brand, int ram, int batteryLife) {
        this.brand = brand;
        this.ram = ram;
        this.batteryLife = batteryLife;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }

    public int getBatteryLife() { return batteryLife; }
    public void setBatteryLife(int batteryLife) { this.batteryLife = batteryLife; }

    @Override
    public void demarrer() {
        System.out.println("[Laptop] " + brand + " demarre sur batterie (" + batteryLife + "h d'autonomie).");
    }
}
