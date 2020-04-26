package com.example.project.controllers;

import com.example.project.entities.Flight;
import com.example.project.repositories.AirplaneRepository;
import com.example.project.repositories.AirportOwnerRepository;
import com.example.project.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class FlightController {
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    AirportOwnerRepository airportOwnerRepository;
    @Autowired
    AirplaneRepository airplaneRepository;

    @PostMapping("add")
    String addFlight() {
        try {
            flightRepository.save(new Flight(LocalDate.now(),
                    LocalDate.now(),
                    airportOwnerRepository.findAll().get(0),
                    airportOwnerRepository.findAll().get(0),
                    airplaneRepository.findAll().get(0),
                    50d));
            return "Dodane";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
