package com.example.demo.controller;

import com.example.demo.model.Laptop;
import com.example.demo.service.LaptopService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Laptop> findAll() {
        return laptopService.findAll();
    }

    @GetMapping("/{id}")
    public Laptop findById(@PathVariable Long id) {
        return laptopService.findById(id);
    }

    @PostMapping
    public Laptop create(@RequestBody Laptop laptop) {
        return laptopService.create(
                laptop.getBrand(), laptop.getRam(), laptop.getBatteryLife());
    }

    @PutMapping("/{id}")
    public Laptop update(@PathVariable Long id, @RequestBody Laptop laptop) {
        return laptopService.update(
                id, laptop.getBrand(), laptop.getRam(), laptop.getBatteryLife());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        laptopService.deleteById(id);
    }
}
