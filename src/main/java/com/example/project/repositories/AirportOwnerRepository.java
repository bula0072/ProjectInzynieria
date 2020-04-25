package com.example.project.repositories;

import com.example.project.entities.users.AirportOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AirportOwnerRepository extends JpaRepository<AirportOwner, Long> {
}
