package com.example.project.controllers.api.airport.service

import com.example.project.dto.AirportNewDto
import com.example.project.entity.Airport
import com.example.project.repository.AirportRepository
import com.example.project.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AirportCreator(
        private val airportRepository: AirportRepository,
        private val userRepository: UserRepository
) {
    fun create(airport: AirportNewDto): Boolean {
        try {
            creator(airport)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    private fun creator(airport: AirportNewDto): Airport {
        if (airport.name.isNullOrEmpty())
            throw Exception("Pusty name")
        if (airportRepository.existsByName(airport.name))
            throw Exception("airport już jest w bazie")
        val user = userRepository.findByLogin(airport.user)
        if (user == null || !user.roleList.contains("AIRPORT_OWNER"))
            throw Exception("user nie istnieje lub nie jest airportOwner")
        if (airportRepository.existsAirportByUserId(user.id))
            throw Exception("user posiada już port lotniczy")
        if (airport.capacity == null || airport.capacity <= 0)
            throw Exception("pojemność portu za mała")
        if (airport.latitude == null || airport.latitude < -90 || airport.latitude > 90)
            throw Exception("niepoprawna szerokość geograficzna")
        if (airport.longitude == null || airport.longitude < -180 || airport.longitude > 180)
            throw Exception("niepoprawna długość geograficzna")
        val newAirport = Airport(
                airport.name,
                airport.capacity,
                airport.latitude,
                airport.longitude,
                true,
                user)
        return airportRepository.save(newAirport)
    }

}
