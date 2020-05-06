package com.example.project.dto.airplanes;

import com.example.project.entities.Airplane;
import com.example.project.interfaces.IGet;
import com.example.project.interfaces.IInfo;
import org.springframework.stereotype.Service;

@Service
public class AirplaneDTO implements IGet<Airplane> {
    @Override
    public IInfo getBasic(Airplane airplane) {
        return new AirplaneBasicDTO(airplane);
    }

    @Override
    public IInfo getOwner(Airplane airplane) {
        return new AirplaneOwnerDTO(airplane);
    }
}
