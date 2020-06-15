package com.example.project.repository;

import com.example.project.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface FlightRepository extends JpaRepository<Flight, Long> {
}
