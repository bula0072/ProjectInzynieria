package com.example.project.controllers.api.airplane.service

import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.AirplaneNewDto
import com.example.project.entity.Airplane
import com.example.project.repository.AirplaneRepository
import org.springframework.stereotype.Service

@Service
class AirplaneCreator(
        private val airplaneRepository: AirplaneRepository,
        private val userApi: UserApi
) {
    fun create(airplane: AirplaneNewDto): Boolean {
        try {
            creator(airplane)
        } catch (e: Exception) {
            print(e.message)
            return false
        }
        return true
    }

    fun creator(airplane: AirplaneNewDto): Airplane {
        if (airplane.name.isNullOrEmpty()) throw Exception("ap name null or blank")
        val user = userApi.getUserByName(airplane.user)
        if (user == null
                || !user.roleList.contains("AIRLINE_OWNER"))
            throw Exception("user nie istnieje lub nie jest właścicielem lotniska")
        if (airplane.capacity == null
                || airplane.capacity <= 0)
            throw Exception("za mała pojemność")
        if (airplane.maxDistance == null
                || airplane.maxDistance <= 0)
            throw Exception("za mały dystans")
        return airplaneRepository.save(Airplane(airplane.name,
                airplane.capacity,
                airplane.maxDistance,
                userApi.getUserByName(airplane.user)))
    }
}
