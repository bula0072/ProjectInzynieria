package com.example.project.repositories;

import com.example.project.entities.users.AirlineOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AirlineOwnerRepository extends JpaRepository<AirlineOwner, Long> {
}
