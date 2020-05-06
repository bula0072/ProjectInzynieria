package com.example.project.controllers;

import com.example.project.dto.airports.AirportDTO;
import com.example.project.entities.users.Airport;
import com.example.project.interfaces.IInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AirportController extends BasicController {
    @Autowired
    AirportDTO airportDTO;

    @GetMapping("airports")
    List<IInfo> getAllAirports() {
        return airportRepository.findAll().stream().map(airportDTO::getBasic).collect(Collectors.toList());
    }

    @GetMapping("airports/{id}")
    IInfo getAirportById(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token", required = false) Long token
    ) {
        try {
            if (airportRepository.findById(id).isEmpty()) throw new NullPointerException();
            Airport airport = airportRepository.findById(id).get();
            if (tokenIsNullOrNotEquals(token, id)) {
                return airportDTO.getBasic(airport);
            }
            return airportDTO.getOwner(airport);
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
            return airportDTO.getFail();
        }
    }

    @PostMapping("airports")
    IInfo postAirport(@RequestParam(name = "token") Long token,
                      @RequestParam(name = "login") String login,
                      @RequestParam(name = "password") String password,
                      @RequestParam(name = "email") String email,
                      @RequestParam(name = "name") String name,
                      @RequestParam(name = "capacity") Integer capacity,
                      @RequestParam(name = "latitude") Double latitude,
                      @RequestParam(name = "longitude") Double longitude) {
        //TODO dodać jeżeli token jest adminem
        if (tokenBelongsToAdmin(token)) {
            return airportDTO.getOwner(airportRepository.save(new Airport(login, password, email, name, capacity, latitude, longitude)));
        }
        return airportDTO.getFail();
    }

    @PatchMapping("airports/{id}")
    IInfo postAirportInfo(
            @PathVariable("id") Long id,
            @RequestParam(name = "token", required = false) Long token,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "capacity", required = false) Integer capacity,
            @RequestParam(name = "latitude", required = false) Double latitude,
            @RequestParam(name = "longitude", required = false) Double longitude
    ) {
        try {
            if (airportRepository.findById(id).isEmpty() || !tokenBelongsToAdminOrOwner(token, id))
                throw new Exception();
            Airport airport = airportRepository.findById(id).get();

            if (!(name == null)) airport.setName(name);
            if (!(capacity == null)) airport.setCapacity(capacity);
            if (!(latitude == null)) airport.setLatitude(latitude);
            if (!(longitude == null)) airport.setLongitude(longitude);

            return airportDTO.getOwner(airportRepository.save(airport));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return airportDTO.getFail();
    }

    @DeleteMapping("airports/{id}")
    List<IInfo> deleteAirport(@PathVariable(name = "id") Long id,
                              @RequestParam(name = "token") Long token) {
        try {
            if (!tokenBelongsToAdmin(token) || airportRepository.findById(id).isEmpty()) throw new Exception();
            airportRepository.delete(airportRepository.findById(id).get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getAllAirports();
    }
}
