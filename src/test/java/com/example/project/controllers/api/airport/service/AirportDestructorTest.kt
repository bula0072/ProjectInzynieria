package com.example.project.controllers.api.airport.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.user.UserApi
import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.dto.AirportNewDto
import com.example.project.dto.UserChangeDto
import com.example.project.repository.AirportRepository
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class AirportDestructorTest @Autowired constructor(
        private val airportRepository: AirportRepository,
        private val airportDestructor: AirportDestructor,
        private val userCreator: UserCreator,
        private val userDestructor: UserDestructor,
        private val userApi: UserApi,
        private val adminApi: AdminApi,
        private val airportCreator: AirportCreator
) {

    @BeforeEach
    fun setUp() {
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "apOwner1",
                        "password",
                        "ap1@gmail.com"
                )), "AIRPORT_OWNER")
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "apOwner2",
                        "password",
                        "ap2@gmail.com"
                )), "AIRPORT_OWNER")
        airportCreator.create(AirportNewDto(
                "newAirport1",
                54,
                65.00,
                3.0,
                "apOwner1"))
        airportCreator.create(AirportNewDto(
                "newAirport2",
                54,
                65.00,
                3.0,
                "apOwner2"))
    }

    @AfterEach
    internal fun tearDown() {
        airportDestructor.deleteAll()
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldBeOk() {
        Assert.assertEquals(
                "should be 2 airports",
                2,
                airportRepository.count())
        Assert.assertTrue(
                "should remove newAirport1",
                airportDestructor.delete("newAirport1"))
        Assert.assertEquals(
                "should be 1 airports",
                1,
                airportRepository.count())
        Assert.assertTrue(
                "should remove newAirport2",
                airportDestructor.delete("newAirport2"))
        Assert.assertEquals(
                "Should be 0 airports",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseAirportWithThisNameDoesntExists(){
        Assert.assertFalse(
                "should be false / wrong Airport name",
                airportDestructor.delete("FakeAirportName"))
        Assert.assertEquals(
                "should be 2 airports",
                2,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseAirportNameIsNull(){
        Assert.assertFalse(
                "should be false / name is null",
                airportDestructor.delete(null))
        Assert.assertEquals(
                "should be 2 airports",
                2,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseAirportNameIsEmpty(){
        Assert.assertFalse(
                "should be false / airport name is empty",
                airportDestructor.delete(""))
    }
}
