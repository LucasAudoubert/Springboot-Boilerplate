package com.example.demo.service;

import com.example.demo.model.Phone;
import com.example.demo.repository.PhoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public Phone create(String brand, Integer ram, String network) {
        Phone phone = new Phone(brand, ram, network);
        return phoneRepository.save(phone);
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public Phone findById(Long id) {
        return phoneRepository.findById(id).orElse(null);
    }
}
