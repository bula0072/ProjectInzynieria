package com.example.project.controllers.api.airline.service

import com.example.project.dto.AirlineChangeDto
import com.example.project.repository.AirlineRepository
import org.springframework.stereotype.Service

/**
 * Obsługuje system edycji obecnie istniejącej już linii lotniczej
 */
@Service
class AirlineEditor(
        private val airlineRepository: AirlineRepository
) {
    /**
     * Obsługuje możliwe błędy wywołane przez <code>editor()</code>
     * @param name stara nazwa linii lotniczej
     * @param newValues nowa nazwa linii lotniczej
     * @return true w przypadku braku błędów
     */
    fun edit(name: String?, newValues: AirlineChangeDto): Boolean {
        try {
            editor(name, newValues)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    /**
     * Edytuje nazwę linii lotniczej, w przypadku źle wypełnionego formularza
     * funkcja wyrzuci błąd
     * @param name stara nazwa linii lotniczej
     * @param newValues nowa nazwa linii lotniczej
     */
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
