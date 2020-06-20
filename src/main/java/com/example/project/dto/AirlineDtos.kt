package com.example.project.dto

import com.example.project.entity.Airline

class AirlineBasicDto(airline: Airline){
    val name: String = airline.name
    val user = UserBasicDto(airline.user)
}

class AirlineNewDto(
        val name: String?,
        val user: String?
)

class AirlineChangeDto(
        val name: String?
)
