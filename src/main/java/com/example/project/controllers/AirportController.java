package com.example.project.controllers;

import com.example.project.dto.AirportDto1;
import com.example.project.entity.Airport;
import com.example.project.repository.AirportRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AirportController {
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    UserRepository userRepository;

    private final String airportUrl = "airports";

    @GetMapping(airportUrl)
    List<AirportDto1> getAllAirports() {
        List<AirportDto1> airportDto1List = new ArrayList<>();
        airportRepository.findAll().forEach(airport -> airportDto1List.add(new AirportDto1(airport)));
        return airportDto1List;
    }

    @GetMapping(airportUrl + "/{id}")
    AirportDto1 getAirportById(
            @PathVariable(name = "id") Long id
    ) {
        return new AirportDto1(airportRepository.findById(id).orElseThrow());
    }

    /*@PostMapping(airportUrl)
    List<AirportDto1> postNewAirport(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "capacity") Integer capacity,
            @RequestParam(name = "latitude") Double latitude,
            @RequestParam(name = "longitude") Double longitude,
            @RequestParam(name = "active", defaultValue = "true") Boolean active,
            @RequestParam(name = "owner") Long ownerId
    ) {
        if (!airportRepository.existsAirportByUserId(ownerId) && userRepository.findByIdAndAccountType_Type(ownerId, "Airport Owner") != null) {
            Airport airport = new Airport(name, capacity, latitude, longitude, active, userRepository.findByIdAndAccountType_Type(ownerId, "Airport Owner"));
            airportRepository.save(airport);
        }
        return getAllAirports();
    }*/

    @PatchMapping(airportUrl + "/{id}")
    List<AirportDto1> patchAirport(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "capacity", required = false) Integer capacity,
            @RequestParam(name = "latitude", required = false) Double latitude,
            @RequestParam(name = "longitude", required = false) Double longitude
    ) {
        //TODO jeżeli są jakieś loty
        if (airportRepository.findById(id).orElseThrow().getActive()) {
            return getAllAirports();
        }
        Airport airport = airportRepository.findById(id).orElseThrow();
        if (name != null) airport.setName(name);
        if (capacity != null) airport.setCapacity(capacity);
        if (latitude != null) airport.setLatitude(latitude);
        if (longitude != null) airport.setLongitude(longitude);
        airport.setActive(true);
        airportRepository.save(airport);
        return getAllAirports();
    }

    @DeleteMapping(airportUrl + "/{id}")
    List<AirportDto1> deleteAirport(
            @PathVariable(name = "id") Long id
    ) {
        //TODO jeżeli są jakieś loty
        if (airportRepository.findById(id).orElseThrow().getActive()) {
            return getAllAirports();
        }
        airportRepository.delete(airportRepository.findById(id).orElseThrow());
        return getAllAirports();
    }

    @PatchMapping(airportUrl + "/{id}/active")
    List<AirportDto1> changeActive(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "active") Boolean active
    ) {
        Airport airport = airportRepository.findById(id).orElseThrow();
        airport.setActive(active);
        airportRepository.save(airport);
        return getAllAirports();
    }
}
