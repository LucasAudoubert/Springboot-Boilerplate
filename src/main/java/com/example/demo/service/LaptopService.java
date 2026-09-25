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
        Laptop laptop = new Laptop(brand, ram, batteryLife);
        return laptopRepository.save(laptop);
    }

    public List<Laptop> findAll() {
        return laptopRepository.findAll();
    }

    public Laptop findById(Long id) {
        return laptopRepository.findById(id).orElse(null);
    }
}
