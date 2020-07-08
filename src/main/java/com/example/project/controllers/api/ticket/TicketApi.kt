package com.example.project.controllers.api.ticket

import com.example.project.controllers.api.ticket.service.TicketCreator
import com.example.project.controllers.api.ticket.service.TicketDestructor
import com.example.project.dto.TicketBasicDto
import com.example.project.dto.TicketNewDto
import com.example.project.entity.Ticket
import com.example.project.repository.TicketRepository
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller obsługujący zapytania pod adres /api/tickets
 */
@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
class TicketApi(
		private val ticketRepository: TicketRepository,
		private val ticketCreator: TicketCreator,
		private val ticketDestructor: TicketDestructor
) {
	/**
	 * Pobieranie z bazy danych wszystkich biletów na loty
	 * @return List<TicketBasicDto>
	 */
	@GetMapping
	fun getAllTicketDto(): List<TicketBasicDto> = getAllTicket().map { t -> TicketBasicDto(t) }

	/**
	 * Pobieranie z bazy danych wszystkich biletów na loty
	 * @return MutableList<Ticket>
	 */
	private fun getAllTicket(): MutableList<Ticket> = ticketRepository.findAll()

	/**
	 * Pobieranie z bazy danych wszystkich biletów na dany lot
	 * @param id id lotu
	 * @return List<TicketBasicDto>
	 */
	@GetMapping("/flight/{id}")
	fun getAllTicketFlightDto(@PathVariable(name = "id") id: Long) = getAllTicketFlight(id).map { t -> TicketBasicDto(t) }

	/**
	 * Pobieranie z bazy danych wszystkich biletów danego użytkownika
	 * @param name nazwa użytkownika
	 * @return List<TicketBasicDto>
	 */
	@GetMapping("/user/{name}")
	fun getAllTicketUser(@PathVariable("name") name: String) =
			ticketRepository.findAll().filter { ticket -> ticket.user.login == name }.map { t -> TicketBasicDto(t) }

	/**
	 * Pobieranie konkretnego biletu z bazy danych
	 * @param id id biletu
	 * @return TicketBasicDto
	 */
	@GetMapping("/{id}")
	fun getTicketDto(@PathVariable("id") id: Long) =
			TicketBasicDto(ticketRepository.findTicketById(id))

	/**
	 * Pobieranie wszystkich biletów na dany lot
	 * @param id id lotu
	 * @return Ticket
	 */
	private fun getAllTicketFlight(id: Long) = ticketRepository
			.findAll()
			.filter { it.flight.id == id }

	/**
	 * Dodanie nowego biletu do bazy danych
	 * @param ticket parametry nowego biletu
	 * @return true, jeżeli można zapisać dany bilet
	 */
	@PostMapping
	fun newTicket(@RequestBody ticket: TicketNewDto) =
			ticketCreator.create(ticket)

	/**
	 * Usunięcie biletu z bazy danych
	 * @param id id biletu
	 * @return true, jeżeli nie było błędów
	 */
	@DeleteMapping("/{id}")
	fun deleteTicket(@PathVariable(name = "id") id: Long) =
			ticketDestructor.delete(id)


}
