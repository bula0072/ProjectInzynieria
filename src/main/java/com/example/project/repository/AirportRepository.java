package com.example.project.repository;

import com.example.project.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AirportRepository extends JpaRepository<Airport, Long> {
    Boolean existsAirportByUserId(Long id);
}
