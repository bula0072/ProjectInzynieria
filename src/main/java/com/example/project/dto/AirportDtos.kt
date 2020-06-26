package com.example.project.dto

import com.example.project.entity.Airport

class AirportBasicDto(airport: Airport) {
    val name: String = airport.name
    val capacity: Int = airport.capacity
    val latitude: Double = airport.latitude
    val longitude: Double = airport.longitude
    val active: Boolean = airport.active
    val user = UserBasicDto(airport.user)
}

class AirportChangeDto(
        val name: String?,
        val capacity: Int?,
        val latitude: Double?,
        val longitude: Double?
)

class AirportNewDto(
        val name: String?,
        val capacity: Int?,
        val latitude: Double?,
        val longitude: Double?,
        val user: String?
)
