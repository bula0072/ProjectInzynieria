package com.example.project.controllers;

import com.example.project.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {
    @Autowired
    FlightRepository flightRepository;
}
