package com.example.project.controllers.api.airport

import com.example.project.controllers.api.airport.service.AirportCreator
import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.AirportBasicDto
import com.example.project.dto.AirportChangeDto
import com.example.project.dto.AirportNewDto
import com.example.project.entity.Airport
import com.example.project.repository.AirportRepository
import com.example.project.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/airports")
class AirportApi(
        private val airportRepository: AirportRepository,
        private val airportCreator: AirportCreator
) {
    @GetMapping
    fun getAllAirportsDto() = airportRepository
            .findAll()
            .map { t -> AirportBasicDto(t) }

    @GetMapping("/{name}")
    fun getAirportByNameDto(@PathVariable(name = "name") name: String) =
            getAirportByName(name)?.let { AirportBasicDto(it) }

    @DeleteMapping("/{name}")
    fun deleteAirport(@PathVariable(name = "name") name: String) =
            airportRepository.delete(getAirportByName(name) as Airport)

    @PatchMapping("/{name}")
    fun patchAirport(
            @PathVariable(name = "name") name: String,
            @RequestBody airportChange: AirportChangeDto
    ) {
        val airport = getAirportByName(name)
        if (airportChange.name != null)
            airport?.name = airportChange.name
        if (airportChange.capacity != null)
            airport?.capacity = airportChange.capacity
        if (airportChange.latitude != null)
            airport?.latitude = airportChange.latitude
        if (airportChange.longitude != null)
            airport?.longitude = airportChange.longitude
        airportRepository.save(airport as Airport)
    }

    @PostMapping
    fun postNewAirport(@RequestBody newAirport: AirportNewDto) =
            airportCreator.create(newAirport)

    fun getAirportByName(name: String): Airport? =
            airportRepository.findByName(name)
}
