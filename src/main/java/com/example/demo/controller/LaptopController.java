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

    // GET /laptop -> tous les laptops
    @GetMapping
    public List<Laptop> findAll() {
        return laptopService.findAll();
    }

    // GET /laptop/{id} -> un laptop
    @GetMapping("/{id}")
    public Laptop findById(@PathVariable Long id) {
        return laptopService.findById(id);
    }

    // POST /laptop -> crée un laptop
    @PostMapping
    public Laptop create(@RequestBody Laptop laptop) {
        return laptopService.create(
                laptop.getBrand(), laptop.getRam(), laptop.getBatteryLife());
    }

    // PUT /laptop/{id} -> modifie un laptop
    @PutMapping("/{id}")
    public Laptop update(@PathVariable Long id, @RequestBody Laptop laptop) {
        return laptopService.update(
                id, laptop.getBrand(), laptop.getRam(), laptop.getBatteryLife());
    }

    // DELETE /laptop/{id} -> supprime un laptop
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        laptopService.deleteById(id);
    }
}
