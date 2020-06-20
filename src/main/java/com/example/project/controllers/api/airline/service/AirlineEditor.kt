package com.example.project.controllers.api.airline.service

import com.example.project.dto.AirlineChangeDto
import com.example.project.repository.AirlineRepository
import org.springframework.stereotype.Service

@Service
class AirlineEditor(
        private val airlineRepository: AirlineRepository
) {
    fun edit(name: String?, newValues: AirlineChangeDto): Boolean {
        try {
            editor(name, newValues)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    private fun editor(name: String?, newValues: AirlineChangeDto) {
        if (airlineRepository.findAirlineByName(name) == null)
            throw Exception("Nie ma takiej lini")
        if (name != newValues.name && airlineRepository.existsByName(newValues.name))
            throw Exception("Linia o takiej nazwie istnieje")
        if (newValues.name.isNullOrEmpty()) throw Exception("podana nazwa jest pusta")
        val airline = airlineRepository.findAirlineByName(name)
        airline.name = newValues.name
        airlineRepository.save(airline)
    }
}
