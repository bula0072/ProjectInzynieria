package com.example.project.dto.airlines;

import com.example.project.entities.users.Airline;
import com.example.project.interfaces.IGet;
import com.example.project.interfaces.IInfo;
import org.springframework.stereotype.Service;

@Service
public class AirlineDTO implements IGet<Airline> {
    @Override
    public IInfo getBasic(Airline airline) {
        return new AirlineBasicDTO(airline);
    }

    @Override
    public IInfo getOwner(Airline airline) {
        return new AirlineOwnerDTO(airline);
    }
}
