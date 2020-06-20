package com.example.project.controllers.api.airplane.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.user.UserApi
import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.dto.AirplaneNewDto
import com.example.project.dto.UserChangeDto
import com.example.project.repository.AirplaneRepository
import com.example.project.repository.UserRepository
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class AirplaneDestructorTest @Autowired constructor(
        private val userApi: UserApi,
        private val airplaneRepository: AirplaneRepository,
        private val userRepository: UserRepository,
        private val airplaneDestructor: AirplaneDestructor,
        private val adminApi: AdminApi,
        private val airplaneCreator: AirplaneCreator,
        private val userCreator: UserCreator
){

    @BeforeEach
    fun setUp() {
        airplaneRepository.deleteAll()
        userRepository.deleteAll()

        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "user",
                        "password",
                        "email@test.com"
                )), "USER")

        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "airlineOwner",
                        "password",
                        "email@tojest.email"
                )), "AIRLINE_OWNER")
    }

    @Test
    fun deleteAirplaneShouldBeCorrect() {
        val apCorrect = AirplaneNewDto("nowy", 50, 50.50, "airlineOwner")
        airplaneCreator.create(apCorrect, userApi)
        val apToRemove = airplaneRepository.findAll().last()
        Assert.assertTrue(airplaneDestructor.delete(apToRemove.id))
    }

    @Test
    fun deleteAirplaneButDoesntExist() {
        val apCorrect = AirplaneNewDto("nowy", 50, 50.50, "airlineOwner")
        airplaneCreator.create(apCorrect, userApi)
        val apLast = airplaneRepository.findAll().last()
        Assert.assertFalse(airplaneDestructor.delete(apLast.id + 1))
    }

    @Test
    fun deleteAirplaneButMinusIndex() {
        val apCorrect = AirplaneNewDto("nowy", 50, 50.50, "airlineOwner")
        airplaneCreator.create(apCorrect, userApi)
        Assert.assertFalse(airplaneDestructor.delete(-1))
    }
}
