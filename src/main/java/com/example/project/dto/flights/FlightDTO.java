package com.example.project.dto.flights;

import com.example.project.entities.Flight;
import com.example.project.interfaces.IGet;
import com.example.project.interfaces.IInfo;
import org.springframework.stereotype.Service;

@Service
public class FlightDTO implements IGet<Flight> {
    @Override
    public IInfo getBasic(Flight flight) {
        return new FlightBasicDTO(flight);
    }

    @Override
    public IInfo getOwner(Flight flight) {
        return new FlightOwnerDTO(flight);
    }
}
