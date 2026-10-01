package com.example.demo.controller;

import com.example.demo.model.Phone;
import com.example.demo.service.PhoneService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping
    public List<Phone> findAll() {
        return phoneService.findAll();
    }

    @GetMapping("/{id}")
    public Phone findById(@PathVariable Long id) {
        return phoneService.findById(id);
    }

    @PostMapping
    public Phone create(@RequestBody Phone phone) {
        return phoneService.create(
                phone.getBrand(), phone.getRam(), phone.getNetwork());
    }

    @PutMapping("/{id}")
    public Phone update(@PathVariable Long id, @RequestBody Phone phone) {
        return phoneService.update(
                id, phone.getBrand(), phone.getRam(), phone.getNetwork());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        phoneService.deleteById(id);
    }
}
