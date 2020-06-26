package com.example.project.controllers.api.airline.service

import com.example.project.dto.AirlineNewDto
import com.example.project.entity.Airline
import com.example.project.repository.AirlineRepository
import com.example.project.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AirlineCreator(
        private val airlineRepository: AirlineRepository,
        private val userRepository: UserRepository
) {
    fun create(airline: AirlineNewDto): Boolean {
        try {
            creator(airline)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    private fun creator(airline: AirlineNewDto): Airline {
        if (airline.name.isNullOrEmpty())
            throw Exception("pusty name")
        if (airlineRepository.existsByName(airline.name))
            throw Exception("airline jest w bazie")
        val user = userRepository.findByLogin(airline.user)
        if (user == null || !user.roleList.contains("AIRLINE_OWNER"))
            throw Exception("user nie istnieje lub nie jest airlineOwner")
        if (airlineRepository.existsAirlineByUserId(user.id))
            throw Exception("use posiada już linie lotnicze")
        val newAirline = Airline(airline.name,
                user)
        return airlineRepository.save(newAirline)
    }
}
