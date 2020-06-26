package com.example.project.repository;

import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);
}
