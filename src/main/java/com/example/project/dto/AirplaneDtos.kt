package com.example.project.dto

import com.example.project.entity.Airplane
import org.springframework.stereotype.Service


class AirplaneBasicDto(
        private val airplane: Airplane
) {
    val id: Long = airplane.id
    val name: String = airplane.name
    val capacity: Int = airplane.capacity
    val maxDistance: Double = airplane.maxDistance
    val user: String = airplane.user.login
}

class AirplaneChangeDto(
    val name: String?,
    val capacity: Int?,
    val maxDistance: Double?
)

class AirplaneNewDto(
        val name: String?,
        val capacity: Int?,
        val maxDistance: Double?,
        val user: String?
)
