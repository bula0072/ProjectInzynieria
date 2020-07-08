package com.example.project.controllers.api.ticket.service

import com.example.project.controllers.api.flight.FlightApi
import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.TicketNewDto
import com.example.project.entity.Ticket
import com.example.project.repository.TicketRepository
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * Dodawanie nowego biletu
 */
@Service
class TicketCreator(
        private val userApi: UserApi,
        private val flightApi: FlightApi,
        private val ticketRepository: TicketRepository
) {
    /**
     * Dodanie nowego lotu do bazy danych
     * @param ticket parametry nowego biletu
     * @return true jeżeli bilet został dodany poprawnie
     */
    fun create(ticket: TicketNewDto): Boolean {
        try {
            creator(ticket)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    /**
     * Sprawdzanie formularza dodania nowego biletu oraz walidacja biletu.
     * Jeżeli wszystko jest poprawnie to bilet zostaje dodany do bazy danych
     * @param newTicket parametry biletu
     * @return Ticket
     */
    private fun creator(newTicket: TicketNewDto): Ticket {
        if (newTicket.flight == null) throw Exception("flight is null")
        val user = userApi.getUserByName(newTicket.user)
        val flight = flightApi.getFlightById(newTicket.flight)
        if (user == null) throw Exception("user is null")
        if (flight == null) throw Exception("flight is null")
        if (Instant.now().isAfter(flight.startDate.minusSeconds(1800)))
            throw Exception("too late, user cannot buy ticket")
        if (newTicket.quantity == null || newTicket.quantity <= 0) throw Exception("ticket is null or less than 1")
        if ((flight.tickets.size + newTicket.quantity) > flight.airplane.capacity)
            throw Exception("not enought space in airplane")
        val ticket = Ticket(
                newTicket.quantity,
                user,
                flight
        )
        return ticketRepository.save(ticket)
    }
}
