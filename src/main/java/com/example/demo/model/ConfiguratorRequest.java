package com.example.demo.model;

/**
 * Requête envoyée par l'utilisateur pour configurer son appareil.
 * Un seul JSON contient le type + tous les champs possibles (certains seront null).
 */
public class ConfiguratorRequest {

    private String type;
    private String brand; // eux c'est pour tous
    private Integer ram;

    private String gpu;             // PC
    private Integer batteryLife;    // LAPTOP
    private String network;         // PHONE

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public Integer getRam() { return ram; }
    public void setRam(Integer ram) { this.ram = ram; }

    public String getGpu() { return gpu; }
    public void setGpu(String gpu) { this.gpu = gpu; }

    public Integer getBatteryLife() { return batteryLife; }
    public void setBatteryLife(Integer batteryLife) { this.batteryLife = batteryLife; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }
}