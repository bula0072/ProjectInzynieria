package com.example.project.repository;

import com.example.project.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findTicketById(Long id);

    List<Ticket> findTicketsByUser_Login(String login);
}
