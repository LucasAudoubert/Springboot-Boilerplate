package com.example.demo.controller;

import com.example.demo.model.PC;
import com.example.demo.service.PcService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // GET /pc -> tous les PC
    @GetMapping
    public List<PC> findAll() {
        return pcService.findAll();
    }

    // GET /pc/{id} -> un PC
    @GetMapping("/{id}")
    public PC findById(@PathVariable Long id) {
        return pcService.findById(id);
    }

    // POST /pc -> crée un PC
    @PostMapping
    public PC create(@RequestBody PC pc) {
        return pcService.create(pc.getBrand(), pc.getRam(), pc.getGpu());
    }

    // PUT /pc/{id} -> modifie un PC
    @PutMapping("/{id}")
    public PC update(@PathVariable Long id, @RequestBody PC pc) {
        return pcService.update(id, pc.getBrand(), pc.getRam(), pc.getGpu());
    }

    // DELETE /pc/{id} -> supprime un PC
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pcService.deleteById(id);
    }
}
