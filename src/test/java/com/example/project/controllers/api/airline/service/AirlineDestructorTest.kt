package com.example.project.controllers.api.airline.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.dto.AirlineNewDto
import com.example.project.dto.UserChangeDto
import com.example.project.repository.AirlineRepository
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class AirlineDestructorTest @Autowired constructor(
        private val userDestructor: UserDestructor,
        private val adminApi: AdminApi,
        private val userCreator: UserCreator,
        private val airlineCreator: AirlineCreator,
        private val airlineRepository: AirlineRepository,
        private val airlineDestructor: AirlineDestructor
) {

    @BeforeEach
    fun setUp() {
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "user",
                        "password",
                        "email1@gmail.com"
                )), "USER")
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "airlineOwner",
                        "password",
                        "email2@gmail.com"
                )), "AIRLINE_OWNER")
        airlineCreator.create(AirlineNewDto(
                "AirlineName",
                "airlineOwner"))
    }

    @AfterEach
    internal fun tearDown() {
        airlineRepository.deleteAll()
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldDeleteAirlineFromDatabase() {
        Assert.assertEquals(
                "Should be 1 airline",
                1,
                airlineRepository.findAll().size)
        Assert.assertTrue(
                "delete should be ok",
                airlineDestructor.delete("AirlineName"))
        Assert.assertEquals(
                "Should be 0 airline",
                0,
                airlineRepository.findAll().size)
    }

    @Test
    fun shouldBeFalseBecauseAirlineDoesntExists() {
        Assert.assertFalse(
                "There is no airline like that",
                airlineDestructor.delete("AirlineFakeName"))
    }

    @Test
    fun shouldBeFalseBecauseAirlineNameIsNull() {
        Assert.assertFalse(
                "airline name is null",
                airlineDestructor.delete(null))
    }

    @Test
    fun shouldBeFalseBecauseAirlineNameIsEmpty() {
        Assert.assertFalse(
                "airline name is empty",
                airlineDestructor.delete("")
        )
    }
}
