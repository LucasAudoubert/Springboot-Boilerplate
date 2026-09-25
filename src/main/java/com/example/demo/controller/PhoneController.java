package com.example.demo.controller;

import com.example.demo.model.Phone;
import com.example.demo.service.PhoneService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Phone create(@RequestBody Phone phone) {
        return phoneService.create(phone.getBrand(), phone.getRam(), phone.getNetwork());
    }
}
