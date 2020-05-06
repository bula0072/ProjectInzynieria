package com.example.project.controllers;

import com.example.project.dto.flights.FlightDTO;
import com.example.project.entities.Flight;
import com.example.project.interfaces.IInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FlightController extends BasicController {
    @Autowired
    FlightDTO flightDTO;

    @GetMapping("flights")
    List<IInfo> getAllFlights() {
        return flightRepository.findAll().stream().map(flightDTO::getBasic).collect(Collectors.toList());
    }

    @GetMapping("flights/{id}")
    IInfo getAirportById(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token", required = false) Long token
    ) {
        try {
            if (flightRepository.findById(id).isEmpty()) throw new NullPointerException();
            Flight flight = flightRepository.findById(id).get();
            if (tokenIsNullOrNotEquals(token, id)) {
                return flightDTO.getBasic(flight);
            }
            return flightDTO.getOwner(flight);
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
            return flightDTO.getFail();
        }
    }

    @PostMapping("flights")
    IInfo postFlight(
            @RequestParam(name = "token") Long token,
            @RequestParam(name = "startdate") String startDate,
            @RequestParam(name = "enddate") String endDate,
            @RequestParam(name = "start") Long startId,
            @RequestParam(name = "end") Long endId,
            @RequestParam(name = "plane") Long planeId,
            @RequestParam(name = "cost") Double cost
    ) {
        try {
            if (
                    airportRepository.findById(startId).isEmpty() ||
                            airportRepository.findById(endId).isEmpty() ||
                            airplaneRepository.findById(planeId).isEmpty()
            ) throw new NullPointerException();

            if (tokenBelongsToAirline(token))
                return flightDTO.getOwner(flightRepository.save(
                        new Flight(
                                LocalDate.parse(startDate),
                                LocalDate.parse(endDate),
                                airportRepository.findById(startId).get(),
                                airportRepository.findById(endId).get(),
                                airplaneRepository.findById(planeId).get(),
                                cost
                        )));
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
        }
        return flightDTO.getFail();
    }

    @DeleteMapping("flights/{id}")
    List<IInfo> deleteFlight(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token") Long token
    ) {
        try {
            if (flightRepository.findById(id).isEmpty()) throw new Exception();
            Flight flight = flightRepository.findById(id).get();
            if (!tokenBelongsToAdmin(token)) throw new Exception();
            flightRepository.delete(flight);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getAllFlights();
    }
}
