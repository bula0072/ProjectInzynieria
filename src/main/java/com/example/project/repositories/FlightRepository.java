package com.example.project.repositories;

import com.example.project.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface FlightRepository extends JpaRepository<Flight, Long> {

}
