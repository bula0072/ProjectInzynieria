package com.example.project.controllers;

import com.example.project.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    AirplaneRepository airplaneRepository;
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    FlightRepository flightRepository;

    Boolean tokenIsNullOrNotEquals(Long token, Long id) {
        return token == null || !token.equals(id);
    }

    Boolean tokenBelongsToAdmin(Long token) {
        return adminRepository.existsById(token);
    }

    Boolean tokenBelongsToAdminOrOwner(Long token, Long id) {
        return adminRepository.existsById(token) || !tokenIsNullOrNotEquals(token, id);
    }

    Boolean tokenBelongsToAirline(Long token) {
        return airlineRepository.existsById(token);
    }
}
