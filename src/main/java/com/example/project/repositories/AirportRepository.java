package com.example.project.repositories;

import com.example.project.entities.users.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AirportRepository extends JpaRepository<Airport, Long> {
}
