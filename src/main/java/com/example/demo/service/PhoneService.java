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
        return phoneRepository.save(new Phone(brand, ram, network));
    }

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public Phone findById(Long id) {
        return phoneRepository.findById(id).orElse(null);
    }

    public Phone update(Long id, String brand, Integer ram, String network) {
        Phone phone = findById(id);
        if (phone == null) {
            return null;
        }
        phone.setBrand(brand);
        phone.setRam(ram);
        phone.setNetwork(network);
        return phoneRepository.save(phone);
    }

    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }
}
