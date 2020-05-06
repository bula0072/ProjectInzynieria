package com.example.project.controllers;

import com.example.project.dto.airlines.AirlineDTO;
import com.example.project.entities.users.Airline;
import com.example.project.interfaces.IInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AirlineController extends BasicController {
    @Autowired
    AirlineDTO airlineDTO;

    @GetMapping("airlines")
    List<IInfo> getAllAirlines() {
        return airlineRepository.findAll().stream().map(airlineDTO::getBasic).collect(Collectors.toList());
    }

    @GetMapping("airlines/{id}")
    IInfo getAirlineInfo(
            @PathVariable("id") Long id,
            @RequestParam(name = "token", required = false) Long token
    ) {
        try {
            if (airlineRepository.findById(id).isEmpty()) throw new NullPointerException();
            Airline airline = airlineRepository.findById(id).get();
            if (tokenIsNullOrNotEquals(token, id)) {
                return airlineDTO.getBasic(airline);
            }
            return airlineDTO.getOwner(airline);
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
            return airlineDTO.getFail();
        }
    }

    @PostMapping("airlines")
    IInfo setAirlines(
            @RequestParam(name = "token") Long token,
            @RequestParam(name = "login") String login,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "name") String name
    ) {
        if (tokenBelongsToAdmin(token))
            return airlineDTO.getOwner(airlineRepository.save(new Airline(login, password, email, name)));
        return airlineDTO.getFail();
    }

    @PatchMapping("airlines/{id}")
    IInfo patchAirline(
            @PathVariable("id") Long id,
            @RequestParam(name = "token", required = false) Long token,
            @RequestParam(name = "name", required = false) String name
    ) {
        try {
            if (airlineRepository.findById(id).isEmpty() || !tokenBelongsToAdminOrOwner(token, id))
                throw new Exception();
            Airline airline = airlineRepository.findById(id).get();

            if (name != null) airline.setName(name);

            return airlineDTO.getOwner(airlineRepository.save(airline));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return airlineDTO.getFail();
    }

    @DeleteMapping("airlines/{id}")
    List<IInfo> deleteAirline(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token") Long token
    ) {
        try {
            if(airlineRepository.findById(id).isEmpty() || !tokenBelongsToAdmin(token)) throw new Exception();
            airlineRepository.delete(airlineRepository.findById(id).get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getAllAirlines();
    }

}
