package com.example.demo.controller;

import com.example.demo.model.ConfiguratorRequest;
import com.example.demo.model.TechInterface;
import com.example.demo.service.ConfiguratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configurator")
public class ConfiguratorController {

    private final ConfiguratorService configuratorService;

    public ConfiguratorController(ConfiguratorService configuratorService) {
        this.configuratorService = configuratorService;
    }

    @PostMapping
    public ResponseEntity<TechInterface> configure(@RequestBody ConfiguratorRequest request) {
        TechInterface device = configuratorService.configure(request);
        return ResponseEntity.ok(device);
    }
}