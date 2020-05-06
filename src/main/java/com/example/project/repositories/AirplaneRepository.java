package com.example.project.repositories;

import com.example.project.entities.Airplane;
import com.example.project.entities.users.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    List<Airplane> getAirplaneByOwner(Airline owner);

    List<Airplane> findAllByOwner(Airline owner);
}
