package com.example.project.controllers.api.airport.service

import com.example.project.dto.AirportChangeDto
import com.example.project.entity.Airport
import com.example.project.repository.AirportRepository
import org.springframework.stereotype.Service

/**
 * Obsługuje system edycji portu lotniczego
 */
@Service
class AirportEditor(
        private val airportRepository: AirportRepository
) {
    /**
     * Obsługuje błędy wywołane przez <code>editor()</code>
     * @param name stara nazwa portu lotniczego
     * @param airport nowe parametry portu lotniczego
     * @return true w przypadku braku błędów
     */
    fun edit(name: String?, airport: AirportChangeDto): Boolean {
        try {
            editor(name, airport)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    /**
     * Obsługuje błędy wywołane przez <code>setter()</code>
     * @param name nazwa portu lotniczego
     * @return true w przypadku braku błędów
     */
    fun setActivity(name: String?): Boolean {
        try {
            setter(name)
        } catch (e: Exception) {
            println(e.message)
            return false
        }
        return true
    }

    /**
     * Zmienia wartość <code>active</code> za false na true i odwrotnie
     * @param name nazwa portu lotniczego
     * @return Airport
     */
    private fun setter(name: String?): Airport {
        if (!airportRepository.existsByName(name))
            throw Exception("Airport nie istnieje")
        val airport = airportRepository.findAirportByName(name)
        airport.active = !airport.active
        return airportRepository.save(airport)
    }

    /**
     * Sprawdza dane wysłane przez formularz zmiany danych portu lotniczego i
     * w przypadku braku błędów modyfikuje dane portu lotniczego
     * @param name stara nazwa portu lotniczego
     * @param newAirport nowe dane portu lotniczego
     * @return Airport
     */
    private fun editor(name: String?, newAirport: AirportChangeDto): Airport {
        if (!airportRepository.existsByName(name))
            throw Exception("Airport nie istnieje")
        val airport = airportRepository.findAirportByName(name)

        if (airport == null || airport.active == true)
            throw Exception("airport jest null lub active jest true")
        if (newAirport.name != null && newAirport.name != airport.name) {
            if (newAirport.name.isEmpty())
                throw Exception("name empty")
            if (airportRepository.existsByName(newAirport.name) && newAirport.name != airport.name)
                throw Exception("taka nazwa już istnieje")
            airport.name = newAirport.name
        }
        if (newAirport.latitude != null && newAirport.latitude != airport.latitude) {
            if (newAirport.latitude < -90
                    || newAirport.latitude > 90)
                throw Exception("szerokość graficzna nieprawidłowa")
            airport.latitude = newAirport.latitude
        }
        if (newAirport.longitude != null && newAirport.longitude != airport.longitude) {
            if (newAirport.longitude < -180
                    || newAirport.longitude > 180)
                throw Exception("długość graficzna nieprawidłowa")
            airport.longitude = newAirport.longitude
        }
        if (newAirport.capacity != null && newAirport.capacity != airport.capacity) {
            if (newAirport.capacity <= 0)
                throw Exception("pojemność nieprawidłowa")
            airport.capacity = newAirport.capacity
        }
        return airportRepository.save(airport)
    }
}
