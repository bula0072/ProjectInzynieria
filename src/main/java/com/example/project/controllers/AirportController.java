package com.example.project.controllers;

import com.example.project.dto.AirportInfoForOwnerDTO;
import com.example.project.dto.AirportInfoForUnregisterDTO;
import com.example.project.entities.users.AirportOwner;
import com.example.project.interfaces.IAirportInfo;
import com.example.project.repositories.AirportOwnerRepository;
import com.example.project.repositories.FlightRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AirportController {
    AirportOwnerRepository airportOwnerRepository;
    FlightRepository flightRepository;

    public AirportController(AirportOwnerRepository airportOwnerRepository, FlightRepository flightRepository) {
        this.airportOwnerRepository = airportOwnerRepository;
        this.flightRepository = flightRepository;
    }

    //TODO token == 3 to tylko przykład, do poprawienia
    @GetMapping("airports/{id}")
    IAirportInfo getAirportInfo(@PathVariable("id") Long id, @RequestParam(name = "token", required = false) Integer token) {
        AirportOwner apo = airportOwnerRepository.findById(id).get();
        if (token == null || token != 3) {
            return new AirportInfoForUnregisterDTO(apo);
        }
        return new AirportInfoForOwnerDTO(apo);
    }

    @PatchMapping("airports/{id}")
    IAirportInfo postAirportInfo(@PathVariable("id") Long id,
                                 @RequestParam(name = "token", required = false) Integer token,
                                 @RequestParam(name = "name", required = false) String name,
                                 @RequestParam(name = "capacity", required = false) Integer capacity,
                                 @RequestParam(name = "latitude", required = false) Double latitude,
                                 @RequestParam(name = "longitude", required = false) Double longitude) {
        AirportOwner apo = airportOwnerRepository.findById(id).get();
        //TODO do zmiany
        if (token == null || token != 3) {
            return new AirportInfoForUnregisterDTO(apo);
        }
        if (!(name == null)) apo.setName(name);
        if (!(capacity == null)) apo.setCapacity(capacity);
        if (!(latitude == null)) apo.setLatitude(latitude);
        if (!(longitude == null)) apo.setLongitude(longitude);

        airportOwnerRepository.save(apo);
        return new AirportInfoForOwnerDTO(apo);
    }

    @GetMapping("airports")
    List<IAirportInfo> getAirports() {
        return getAllAirportsforUnregister();
    }

    List<IAirportInfo> getAllAirportsforUnregister() {
        List<IAirportInfo> airportInfoForUnregisterDTOList = new ArrayList<>();
        for (AirportOwner airportOwner : airportOwnerRepository.findAll()) {
            airportInfoForUnregisterDTOList.add(new AirportInfoForUnregisterDTO(airportOwner));
        }
        return airportInfoForUnregisterDTOList;
    }

    @PostMapping("airports")
    List<IAirportInfo> postAirport(@RequestParam(name = "token") Double token,
                                   @RequestParam(name = "login") String login,
                                   @RequestParam(name = "password") String password,
                                   @RequestParam(name = "email") String email,
                                   @RequestParam(name = "name") String name,
                                   @RequestParam(name = "capacity") Integer capacity,
                                   @RequestParam(name = "latitude") Double latitude,
                                   @RequestParam(name = "longitude") Double longitude) {
        //TODO dodać jeżeli token jest adminem
        if (token != null) {
            AirportOwner apo = new AirportOwner(login, password, email, name, capacity, latitude, longitude);
            airportOwnerRepository.save(apo);
        }
        return getAllAirportsforUnregister();
    }

    @DeleteMapping("airports/{id}")
    List<IAirportInfo> deleteAirport(@PathVariable(name = "id") Long id,
                                     @RequestParam(name = "token") Long token) {
        AirportOwner apo = airportOwnerRepository.findById(id).get();
        if (token != null && token.equals(id)) {
            airportOwnerRepository.delete(apo);
        }
        return getAllAirportsforUnregister();
    }


}
