package com.example.demo.service;

import com.example.demo.model.ConfiguratorRequest;
import com.example.demo.model.TechInterface;
import org.springframework.stereotype.Service;

@Service
public class ConfiguratorService {

    private final PcService pcService;
    private final LaptopService laptopService;
    private final PhoneService phoneService;

    public ConfiguratorService(PcService pcService,
                                LaptopService laptopService,
                                PhoneService phoneService) {
        this.pcService = pcService;
        this.laptopService = laptopService;
        this.phoneService = phoneService;
    }

    public TechInterface configure(ConfiguratorRequest req) {
        if (req.getType() == null) {
            throw new IllegalArgumentException("Le champ 'type' est obligatoire (PC, LAPTOP ou PHONE)");
        }

        switch (req.getType().toUpperCase()) {
            case "PC":
                return pcService.create(req.getBrand(), req.getRam(), req.getGpu());

            case "LAPTOP":
                return laptopService.create(req.getBrand(), req.getRam(), req.getBatteryLife());

            case "PHONE":
                return phoneService.create(req.getBrand(), req.getRam(), req.getNetwork());

            default:
                throw new IllegalArgumentException(
                    "Type inconnu : " + req.getType() + " (PC, LAPTOP ou PHONE)");
        }
    }
}