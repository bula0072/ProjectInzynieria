package com.example.project.repository;

import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface UserRepository extends JpaRepository<User, Long> {

    void deleteById(Long id);

    User findByLogin(String login);
}
