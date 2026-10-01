package com.example.demo.repository;

import com.example.demo.model.PC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PcRepository extends JpaRepository<PC, Long> {
}
