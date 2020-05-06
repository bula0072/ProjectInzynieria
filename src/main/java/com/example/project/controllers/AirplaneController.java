package com.example.project.controllers;

import com.example.project.dto.airplanes.AirplaneDTO;
import com.example.project.entities.Airplane;
import com.example.project.interfaces.IInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AirplaneController extends BasicController {
    @Autowired
    AirplaneDTO airplaneDTO;

    @GetMapping("airplanes")
    List<IInfo> getAllAirplanes() {
        return airplaneRepository.findAll().stream().map(airplaneDTO::getBasic).collect(Collectors.toList());
    }

    @GetMapping("airplanes/{id}")
    IInfo getAllAirplanes(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token", required = false) Long token
    ) {
        try {
            if (airplaneRepository.findById(id).isEmpty()) throw new NullPointerException();
            Airplane airplane = airplaneRepository.findById(id).get();
            if (tokenIsNullOrNotEquals(token, id)) {
                return airplaneDTO.getBasic(airplane);
            }
            return airplaneDTO.getOwner(airplane);
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
            return airplaneDTO.getFail();
        }
    }

    @PostMapping("airplanes")
    IInfo postNewAirplane(
            @RequestParam(name = "token") Long token,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "capacity") Integer capacity,
            @RequestParam(name = "maxdistance") Double maxDistance,
            @RequestParam(name = "owner") Long ownerId
    ) {
        if (tokenBelongsToAdminOrOwner(token, ownerId))
            try {
                if (airlineRepository.findById(ownerId).isEmpty()) throw new NullPointerException();
                return airplaneDTO.getOwner(airplaneRepository
                        .save(new Airplane(
                                name,
                                capacity,
                                maxDistance,
                                airlineRepository.findById(ownerId).get()
                        )));
            } catch (NullPointerException exception) {
                System.out.println(exception.getMessage());
            }
        return airplaneDTO.getFail();
    }

    @PatchMapping("airplanes/{id}")
    IInfo patchAirplane(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token") Long token,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "capacity", required = false) Integer capacity,
            @RequestParam(name = "maxDistance", required = false) Double maxDistance
    ) {
        try {
            if (airplaneRepository.findById(id).isEmpty()) throw new Exception();
            Airplane airplane = airplaneRepository.findById(id).get();
            if (!tokenBelongsToAdminOrOwner(token, airplane.getOwner().getId())) throw new Exception();

            if (name != null) airplane.setName(name);
            if (capacity != null) airplane.setCapacity(capacity);
            if (maxDistance != null) airplane.setMaxDistance(maxDistance);

            return airplaneDTO.getOwner(airplaneRepository.save(airplane));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return airplaneDTO.getFail();
    }

    @DeleteMapping("airplanes/{id}")
    List<IInfo> deleteAirplane(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "token") Long token
    ) {
        try {
            if (airplaneRepository.findById(id).isEmpty()) throw new Exception();
            Airplane airplane = airplaneRepository.findById(id).get();
            if (!tokenBelongsToAdminOrOwner(token, airplane.getOwner().getId())) throw new Exception();
            airplaneRepository.delete(airplaneRepository.findById(id).get());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return getAllAirplanes();
    }
}
