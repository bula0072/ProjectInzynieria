package com.example.project.controllers.api.user

import com.example.project.controllers.api.airline.FlightApi
import com.example.project.dto.TicketBasicDto
import com.example.project.dto.TicketNewDto
import com.example.project.entity.Ticket
import com.example.project.repository.TicketRepository
import org.springframework.web.bind.annotation.*
//TODO testy i servisy
@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
class TicketApi(
        private val ticketRepository: TicketRepository,
        private val userApi: UserApi,
        private val flightApi: FlightApi
) {

    @GetMapping
    fun getAllTicketDto(): List<TicketBasicDto> = getAllTicket().map { t -> TicketBasicDto(t) }

    private fun getAllTicket(): MutableList<Ticket> = ticketRepository.findAll()

    @GetMapping("/flight/{id}")
    fun getAllTicketFlightDto(@PathVariable(name = "id") id: Long) = getAllTicketFlight(id)

    private fun getAllTicketFlight(id: Long) = ticketRepository
            .findAll()
            .filter { it.flight.id == id }

    //TODO nie można, jeżeli nie będzie już miejsca
    @PostMapping
    fun newTicket(@RequestBody newTicket: TicketNewDto) {
        val user = userApi.getUserByName(newTicket.user)
        val flight = flightApi.getFlightById(newTicket.flight)
        ticketRepository.save(Ticket(newTicket.quantity, user, flight))
    }

    //TODO delety max 1h przed wylotem
    @DeleteMapping("/{id}")
    fun deleteTicket(@PathVariable(name = "id") id: Long) {
        val ticket = ticketRepository.findTicketById(id)
        ticketRepository.delete(ticket)
    }
}
