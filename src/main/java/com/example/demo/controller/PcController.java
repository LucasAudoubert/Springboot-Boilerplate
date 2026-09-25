package com.example.demo.controller;

import com.example.demo.model.PC;
import com.example.demo.service.PcService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pc")
public class PcController {

    private final PcService pcService;

    public PcController(PcService pcService) {
        this.pcService = pcService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @PostMapping("/hello")
    public String goodbyeWorld() {
        return "goodbyeWorld";
    }

    @PostMapping
    public PC create(@RequestBody PC pc) {
        return pcService.create(pc.getBrand(), pc.getRam(), pc.getGpu());
    }
}