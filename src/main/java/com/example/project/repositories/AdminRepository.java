package com.example.project.repositories;

import com.example.project.entities.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
