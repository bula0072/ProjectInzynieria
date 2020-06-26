package com.example.project.controllers.api.airplane

import com.example.project.controllers.api.airplane.service.AirplaneCreator
import com.example.project.controllers.api.airplane.service.AirplaneDestructor
import com.example.project.dto.AirplaneBasicDto
import com.example.project.dto.AirplaneNewDto
import com.example.project.entity.Airplane
import com.example.project.repository.AirplaneRepository
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/airplanes")
class AirplaneApi(
        private val airplaneRepository: AirplaneRepository,
        private val airplaneDestructor: AirplaneDestructor,
        private val airplaneCreator: AirplaneCreator
) {

    @GetMapping
    fun getAllAirplanesDto() = airplaneRepository
            .findAll()
            .map { t -> AirplaneBasicDto(t) }


    @GetMapping("/{id}")
    fun getAirplaneByIdDto(@PathVariable(name = "id") id: Long) =
            getAirplaneById(id)?.let { AirplaneBasicDto(it) }

    @GetMapping("/user/{username}")
    fun getAirplanesByUsernameDto(@PathVariable(name = "username") username: String) =
            airplaneRepository
                    .findAllByUser_Login(username)
                    .map { t -> AirplaneBasicDto(t) }

    @DeleteMapping("/{id}")
    fun deleteAirplane(@PathVariable(name = "id") id: Long): Boolean =
            airplaneDestructor.delete(id)

    @PostMapping
    fun postNewAirplane(@RequestBody newAirplane: AirplaneNewDto): Boolean =
            airplaneCreator.create(newAirplane)

    fun getAllAirplanes(): MutableList<Airplane> = airplaneRepository.findAll()

    fun getAirplaneById(id: Long): Airplane? =
            airplaneRepository.findAirplaneById(id)
}
