package com.example.project.controllers.api.airplane.service

import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.AirplaneNewDto
import com.example.project.entity.Airplane
import com.example.project.entity.User
import com.example.project.repository.AirplaneRepository
import org.springframework.stereotype.Service

@Service
class AirplaneDestructor(
        private val airplaneRepository: AirplaneRepository
) {
    // TODO ograniczyć możliwość do właścicieli i administratorów
    fun delete(id: Long): Boolean {
        return when {
            !airplaneRepository.existsById(id) -> false
            else -> {
                airplaneRepository.delete(airplaneRepository.findAirplaneById(id) as Airplane)
                true
            }
        }
    }
}