package com.example.demo.service;

import com.example.demo.model.Laptop;
import com.example.demo.repository.LaptopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaptopService {

    private final LaptopRepository laptopRepository;

    public LaptopService(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    public Laptop create(String brand, Integer ram, Integer batteryLife) {
        return laptopRepository.save(new Laptop(brand, ram, batteryLife));
    }

    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    public Laptop findById(Long id) {
        return laptopRepository.findById(id).orElse(null);
    }

    public Laptop update(Long id, String brand, Integer ram, Integer batteryLife) {
        Laptop laptop = findById(id);
        if (laptop == null) {
            return null;
        }
        laptop.setBrand(brand);
        laptop.setRam(ram);
        laptop.setBatteryLife(batteryLife);
        return laptopRepository.save(laptop);
    }

    public void deleteById(Long id) {
        laptopRepository.deleteById(id);
    }
}
