package com.example.project.controllers;

import com.example.project.dto.AirplaneDto1;
import com.example.project.entity.Airplane;
import com.example.project.repository.AirplaneRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AirplaneController {
    @Autowired
    AirplaneRepository airplaneRepository;
    @Autowired
    UserRepository userRepository;

    private final String airplaneUrl = "airplanes";

    @GetMapping(airplaneUrl)
    List<AirplaneDto1> getAllAirplane() {
        return airplaneRepository.findAll().stream().map(AirplaneDto1::new).collect(Collectors.toList());
    }

    @GetMapping(airplaneUrl + "/user/{id}")
    List<AirplaneDto1> getAirplanesByUserId(
            @PathVariable(name = "id") Long id
    ) {
        return airplaneRepository
                .findAllByUser_Id(id)
                .stream()
                .map(AirplaneDto1::new)
                .collect(Collectors.toList());
    }

    @GetMapping(airplaneUrl + "/{id}")
    AirplaneDto1 getAirplaneById(
            @PathVariable(name = "id") Long id
    ) {
        return new AirplaneDto1(airplaneRepository.findById(id).orElseThrow());
    }

    /*@PostMapping(airplaneUrl)
    List<AirplaneDto1> postAirplane(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "capacity") Integer capacity,
            @RequestParam(name = "maxDistance") Double maxDistance,
            @RequestParam(name = "owner") Long ownerId
    ) {
        if (userRepository.findByIdAndAccountType_Type(ownerId, "Airline Owner") != null) {
            Airplane airplane = new Airplane(name, capacity, maxDistance, userRepository.findByIdAndAccountType_Type(ownerId, "Airline Owner"));
            airplaneRepository.save(airplane);
        }
        return getAllAirplane();
    }*/

    @PatchMapping(airplaneUrl + "/{id}")
    List<AirplaneDto1> patchAirplane(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "capacity", required = false) Integer capacity,
            @RequestParam(name = "maxDistance", required = false) Double maxDistance
    ) {
        Airplane airplane = airplaneRepository.findById(id).orElseThrow();
        if (name != null) airplane.setName(name);
        if (capacity != null) airplane.setCapacity(capacity);
        if (maxDistance != null) airplane.setMaxDistance(maxDistance);
        airplaneRepository.save(airplane);
        return getAllAirplane();
    }

    @DeleteMapping(airplaneUrl + "/{id}")
    List<AirplaneDto1> deletaAirplane(
            @PathVariable(name = "id") Long id
    ) {
        airplaneRepository.delete(airplaneRepository.findById(id).orElseThrow());
        return getAllAirplane();
    }
}
