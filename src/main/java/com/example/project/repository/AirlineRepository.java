package com.example.project.repository;

import com.example.project.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AirlineRepository extends JpaRepository<Airline, Long> {
    Boolean existsAirlineByUserId(Long id);

    Airline findAirlineByName(String name);

    Boolean existsByName(String name);
}
