package com.example.project.controllers.api.ticket.service

import com.example.project.controllers.api.ticket.TicketApi
import com.example.project.dto.TicketBasicDto
import com.example.project.repository.TicketRepository
import org.springframework.stereotype.Service
import java.time.Instant

/**
 * Usunięcie biletu z bazy danych
 */
@Service
class TicketDestructor(
        private val ticketRepository: TicketRepository
) {
    /**
     * Usunięcie wszystkich biletów z bazy danych
     * @return true jeżeli nie było żanych błędów
     */
    fun deleteAll(): Boolean {
        try {
            ticketRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    /**
     * Usunięcie konkretnego biletu
     * @param id id biletu
     * @return true jeżeli nie było żadnych błędów
     */
    fun delete(id: Long): Boolean {
        try {
            destructor(id)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    /**
     * Sprawdzenie, czy bilet można usunąć. Jeżeli można, to bilet zostaje usunięty.
     * W przeciwyn wypadku wyrzuca błąd
     * @param id id biletu
     */
    private fun destructor(id: Long) {
        val ticket = TicketBasicDto(ticketRepository.findTicketById(id))
        if (Instant.now().isAfter(ticket.flight.startDate.minusSeconds(1800)))
            throw Exception("Too late, you cant delete this thicket")
        ticketRepository.deleteById(id)
    }
}
