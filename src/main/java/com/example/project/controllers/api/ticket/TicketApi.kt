package com.example.project.controllers.api.ticket

import com.example.project.controllers.api.flight.FlightApi
import com.example.project.controllers.api.ticket.service.TicketCreator
import com.example.project.controllers.api.ticket.service.TicketDestructor
import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.TicketBasicDto
import com.example.project.dto.TicketNewDto
import com.example.project.entity.Ticket
import com.example.project.repository.TicketRepository
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
class TicketApi(
        private val ticketRepository: TicketRepository,
        private val userApi: UserApi,
        private val flightApi: FlightApi,
        private val ticketCreator: TicketCreator,
        private val ticketDestructor: TicketDestructor
) {

    @GetMapping
    fun getAllTicketDto(): List<TicketBasicDto> = getAllTicket().map { t -> TicketBasicDto(t) }

    private fun getAllTicket(): MutableList<Ticket> = ticketRepository.findAll()

    @GetMapping("/flight/{id}")
    fun getAllTicketFlightDto(@PathVariable(name = "id") id: Long) = getAllTicketFlight(id).map { t -> TicketBasicDto(t) }

    @GetMapping("/user/{name}")
    fun getAllTicketUser(@PathVariable("name") name: String) {
        ticketRepository.findTicketsByUser_Login(name).map { t -> TicketBasicDto(t) }
    }

    @GetMapping("/{id}")
    fun getTicketDto(@PathVariable("id") id: Long) =
            TicketBasicDto(ticketRepository.findTicketById(id))


    private fun getAllTicketFlight(id: Long) = ticketRepository
            .findAll()
            .filter { it.flight.id == id }

    //TODO nie można, jeżeli nie będzie już miejsca
    @PostMapping
    fun newTicket(@RequestBody ticket: TicketNewDto) =
            ticketCreator.create(ticket)

    //TODO delety max 1h przed wylotem
    @DeleteMapping("/{id}")
    fun deleteTicket(@PathVariable(name = "id") id: Long) =
            ticketDestructor.delete(id)


}
