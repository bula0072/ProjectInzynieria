package com.example.project.repositories;

import com.example.project.entities.users.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
