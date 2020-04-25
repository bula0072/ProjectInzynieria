package com.example.project.repositories;

import com.example.project.entities.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
}
