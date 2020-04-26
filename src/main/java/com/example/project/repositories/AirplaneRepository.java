package com.example.project.repositories;

import com.example.project.entities.Airplane;
import com.example.project.entities.users.AirlineOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    List<Airplane> getAirplaneByOwner(AirlineOwner owner);

    List<Airplane> findAllByOwner(AirlineOwner owner);
}
