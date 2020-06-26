package com.example.project.dto

import com.example.project.entity.Ticket

class TicketBasicDto(
        private val ticket: Ticket
) {
    val id: Long = ticket.id
    val quantity: Int = ticket.quantity
    val user: UserBasicDto = UserBasicDto(ticket.user)
    val flight: FlightBasicDto = FlightBasicDto(ticket.flight)
}

class TicketNewDto(
        val quantity: Int?,
        val user: String?,
        val flight: Long?
)
