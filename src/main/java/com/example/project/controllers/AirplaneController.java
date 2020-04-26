package com.example.project.controllers;

import com.example.project.entities.Airplane;
import com.example.project.repositories.AirlineOwnerRepository;
import com.example.project.repositories.AirplaneRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirplaneController {
    AirplaneRepository repository;
    AirlineOwnerRepository ownerRepository;

    public AirplaneController(AirplaneRepository repository,
                              AirlineOwnerRepository ownerRepository) {
        this.repository = repository;
        this.ownerRepository = ownerRepository;
    }

    @GetMapping("planes")
    List<Airplane> air() {
        return repository.findAll();
    }

    @GetMapping("plaown")
    List<Airplane> air2() {
        return repository.findAllByOwner(ownerRepository.findAll().get(0));
    }
}
