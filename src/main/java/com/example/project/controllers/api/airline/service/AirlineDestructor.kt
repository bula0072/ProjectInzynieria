package com.example.project.controllers.api.airline.service

import com.example.project.repository.AirlineRepository
import org.springframework.stereotype.Service

@Service
class AirlineDestructor(
        private val airlineRepository: AirlineRepository
) {
    fun delete(name: String?): Boolean {
        try {
            val airline = airlineRepository.findAirlineByName(name)
            airlineRepository.delete(airline)
        } catch (e: Exception) {
            return false
        }
        return true
    }

    fun deleteAll(): Boolean {
        try {
            airlineRepository.deleteAll()
        } catch (e: Exception) {
            return false
        }
        return true
    }
}
