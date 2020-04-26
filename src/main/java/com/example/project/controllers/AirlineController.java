package com.example.project.controllers;

import com.example.project.dto.AirlineInfoForOwnerDTO;
import com.example.project.dto.AirlineInfoForUnregisteredDTO;
import com.example.project.dto.AirplaneDTO;
import com.example.project.entities.users.AirlineOwner;
import com.example.project.interfaces.IAirlineInfo;
import com.example.project.repositories.AirlineOwnerRepository;
import com.example.project.repositories.AirplaneRepository;
import com.example.project.repositories.FlightRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AirlineController {
    AirlineOwnerRepository airlineOwnerRepository;
    FlightRepository flightRepository;
    AirplaneRepository airplaneRepository;

    public AirlineController(AirlineOwnerRepository airlineOwnerRepository,
                             FlightRepository flightRepository,
                             AirplaneRepository airplaneRepository) {
        this.airlineOwnerRepository = airlineOwnerRepository;
        this.flightRepository = flightRepository;
        this.airplaneRepository = airplaneRepository;
    }

    @GetMapping("airlines")
    List<AirlineInfoForUnregisteredDTO> getAirlines() {
        List<AirlineInfoForUnregisteredDTO> info = new ArrayList<>();
        airlineOwnerRepository.findAll().forEach(airlineOwner -> {
            List<AirplaneDTO> plane = new ArrayList<>();
            airplaneRepository.findAllByOwner(airlineOwner).forEach(airplanetosend -> {
                plane.add(new AirplaneDTO(airplanetosend.getName(), airplanetosend.getCapacity(), airplanetosend.getMaxDistance()));
            });
            info.add(new AirlineInfoForUnregisteredDTO(airlineOwner.getName(), plane));
        });
        return info;
    }

    @GetMapping("airlines/{id}")
    IAirlineInfo getAirlineInfo(
            @PathVariable("id") Long id,
            @RequestParam(name = "token", required = false) Long token
    ) {
        AirlineOwner airlineOwner = airlineOwnerRepository.findById(id).get();
        List<AirplaneDTO> plane = new ArrayList<>();
        airplaneRepository.findAllByOwner(airlineOwner).forEach(p -> {
            plane.add(new AirplaneDTO(p.getName(), p.getCapacity(), p.getMaxDistance()));
        });
        //TODO token przykładowy, zmienić później
        if (token == null || token != airlineOwner.getId()) {
            //TODO zmienić tak jak tutaj jest dla innych jeżeli zadziała
            return new AirlineInfoForUnregisteredDTO(airlineOwner, plane);
        }
        return new AirlineInfoForOwnerDTO(airlineOwner, plane);
    }

    @PostMapping("airlines")
    List<AirlineInfoForUnregisteredDTO> setAirlines(
            @RequestParam(name = "token") Double token,
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "name") String name
    ) {
        //TODO dodać jeżeli token jest adminem
        if (token != null) {
            AirlineOwner airlineOwner = new AirlineOwner(login, password, email, name);
            airlineOwnerRepository.save(airlineOwner);
        }
        return getAirlines();
    }

}
