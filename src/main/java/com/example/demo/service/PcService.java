package com.example.demo.service;

import com.example.demo.model.PC;
import com.example.demo.repository.PcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcService {

    private final PcRepository pcRepository;  // AVANT : pas de repository

    public PcService(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    // AVANT : return new PC(...)  → objet perdu
    // APRÈS : pcRepository.save(...) → sauvegardé en BD
    public PC create(String brand, Integer ram, String gpu) {
        PC pc = new PC(brand, ram, gpu);
        return pcRepository.save(pc);
    }

    // NOUVEAU : lire tous les PC depuis la BD
    public List<PC> findAll() {
        return pcRepository.findAll();
    }

    // NOUVEAU : lire un PC par son id
    public PC findById(Long id) {
        return pcRepository.findById(id).orElse(null);
    }
}