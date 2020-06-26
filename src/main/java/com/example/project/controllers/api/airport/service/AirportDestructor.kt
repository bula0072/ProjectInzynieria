package com.example.project.controllers.api.airport.service

import com.example.project.repository.AirportRepository
import org.springframework.stereotype.Service

@Service
class AirportDestructor(
        private val airportRepository: AirportRepository
) {
    fun delete(name: String?): Boolean {
        try {
            val airport = airportRepository.findAirportByName(name)
            airportRepository.delete(airport)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    fun deleteAll(): Boolean {
        try {
            airportRepository.deleteAll()
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

}
