package com.example.demo.controller;

import com.example.demo.model.Laptop;
import com.example.demo.service.LaptopService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/laptop")
public class LaptopController {

    private final LaptopService laptopService;

    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @PostMapping
    public Laptop create(@RequestBody Laptop laptop) {
        return laptopService.create(laptop.getBrand(), laptop.getRam(), laptop.getBatteryLife());
    }
}