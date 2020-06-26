package com.example.project.repository;

import com.example.project.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {
    List<Airplane> findAllByUser_Login(String username);

    Boolean existsAirplaneByUserId(Long id);

    Airplane findAirplaneById(Long id);


}
