package com.example.demo.service;

import com.example.demo.model.PC;
import com.example.demo.repository.PcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcService {

    private final PcRepository pcRepository;

    public PcService(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    public PC create(String brand, Integer ram, String gpu) {
        return pcRepository.save(new PC(brand, ram, gpu));
    }

    public List<PC> findAll() {
        return pcRepository.findAll();
    }

    public PC findById(Long id) {
        return pcRepository.findById(id).orElse(null);
    }

    public PC update(Long id, String brand, Integer ram, String gpu) {
        PC pc = findById(id);
        if (pc == null) {
            return null;
        }
        pc.setBrand(brand);
        pc.setRam(ram);
        pc.setGpu(gpu);
        return pcRepository.save(pc);
    }

    public void deleteById(Long id) {
        pcRepository.deleteById(id);
    }
}
