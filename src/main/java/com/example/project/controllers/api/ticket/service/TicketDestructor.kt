package com.example.project.controllers.api.ticket.service

import com.example.project.controllers.api.ticket.TicketApi
import com.example.project.dto.TicketBasicDto
import com.example.project.repository.TicketRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TicketDestructor(
        private val ticketRepository: TicketRepository
) {
    fun deleteAll(): Boolean {
        try {
            ticketRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun delete(id: Long): Boolean {
        try {
            destructor(id)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun destructor(id: Long) {
        val ticket = TicketBasicDto(ticketRepository.findTicketById(id))
        if (Instant.now().isBefore(ticket.flight.startDate.minusSeconds(1800)))
            throw Exception("Too late, you cant delete this thicket")
        ticketRepository.deleteById(id)
    }
}
