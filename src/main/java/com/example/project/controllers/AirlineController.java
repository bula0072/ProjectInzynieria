package com.example.project.controllers;

import com.example.project.dto.AirlineDto1;
import com.example.project.entity.Airline;
import com.example.project.repository.AirlineRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AirlineController {
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("airlines")
    List<AirlineDto1> getAllAirlines() {
        List<AirlineDto1> airlines = new ArrayList<>();
        airlineRepository.findAll().forEach(airline -> airlines.add(new AirlineDto1(airline)));
        return airlines;
    }

    @GetMapping("airlines/{id}")
    AirlineDto1 getAirlineById(
            @PathVariable(name = "id") Long id
    ) {
        return new AirlineDto1(airlineRepository.findById(id).orElseThrow());
    }

    /*@PostMapping("airlines")
    List<AirlineDto1> postNewAirline(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "user") Long id
    ) {
        if (!airlineRepository.existsAirlineByUserId(id) && userRepository.findByIdAndAccountType_Type(id, "Airline Owner") != null) {
            Airline airline = new Airline(name, userRepository.findByIdAndAccountType_Type(id, "Airline Owner"));
            airlineRepository.save(airline);
        }
        return getAllAirlines();
    }*/

    @DeleteMapping("airlines/{id}")
    List<AirlineDto1> deleteAirline(
            @PathVariable(name = "id") Long id
    ) {
        airlineRepository.deleteById(id);
        return getAllAirlines();
    }

    @PatchMapping("airlines/{id}")
    List<AirlineDto1> patchAirline(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "name") String name
    ) {
        Airline airline = airlineRepository.findById(id).orElseThrow();
        if (name != null) airline.setName(name);
        airlineRepository.save(airline);
        return getAllAirlines();
    }

}
