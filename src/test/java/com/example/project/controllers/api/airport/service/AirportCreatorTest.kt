package com.example.project.controllers.api.airport.service

import com.example.project.controllers.api.admin.AdminApi
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
internal class AirportCreatorTest @Autowired constructor(
        private val airportDestructor: AirportDestructor,
        private val userDestructor: UserDestructor,
        private val userCreator: UserCreator,
        private val adminApi: AdminApi,
        private val airportCreator: AirportCreator,
        private val airportRepository: AirportRepository
) {

    @BeforeEach
    fun setUp() {
        userCreator.createUser(UserChangeDto("user",
                "password",
                "user@email.com"))
        adminApi.changeRole(userCreator.create(UserChangeDto("apOwner1",
                "password",
                "apOwner1@email.com")),
                "AIRPORT_OWNER")
        adminApi.changeRole(userCreator.create(UserChangeDto("apOwner2",
                "password",
                "apOwner2@email.com")),
                "AIRPORT_OWNER")
    }

    @AfterEach
    internal fun tearDown() {
        airportDestructor.deleteAll()
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldBeOk() {
        Assert.assertEquals(
                "should be 0",
                0,
                airportRepository.count())
        Assert.assertTrue(
                "should be true",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be 1",
                1,
                airportRepository.count())
        Assert.assertTrue(
                "should be true",
                airportCreator.create(AirportNewDto(
                        "newAirport123",
                        54,
                        65.00,
                        3.0,
                        "apOwner2")))
        Assert.assertEquals(
                "should be 2",
                2,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseUserIsEmpty() {
        Assert.assertFalse(
                "should be false / empty username",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        128.00,
                        "")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseUserIsNull() {
        Assert.assertFalse(
                "should be false / null username",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        128.00,
                        null)))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseUserDoesntExists() {
        Assert.assertFalse(
                "should be false / wrong username",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        128.00,
                        "fakeUsername")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseNameIsEmpty() {
        Assert.assertFalse(
                "should be false / empty name",
                airportCreator.create(AirportNewDto(
                        "",
                        50,
                        -30.00,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseNameIsNull() {
        Assert.assertFalse(
                "add new airport should be false / null name",
                airportCreator.create(AirportNewDto(
                        null,
                        50,
                        -30.00,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseCapacityIsNull() {
        Assert.assertFalse(
                "should be false / null capacity",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        null,
                        -30.00,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseLatitudeIsNull() {
        Assert.assertFalse(
                "should be false / null latitude",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        null,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseLongitudeIsNull() {
        Assert.assertFalse(
                "should be false / null longitude",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        null,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseCapacityIsZero() {
        Assert.assertFalse(
                "should be false / capacity is 0",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        0,
                        -30.00,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseCapacityIsLessThanZero() {
        Assert.assertFalse(
                "should be false / capacity < 0",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        -30,
                        -30.00,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseLatitudeIsLessThanMinus90() {
        Assert.assertFalse(
                "should be false / latitude less than -90",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -91.0,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseLatitudeIsMoreThan90(){
        Assert.assertFalse(
                "should be false / latitude more than 90",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        91.0,
                        128.00,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseLongitudeIsLessThanMinis180(){
        Assert.assertFalse(
                "should be false / longitude less than -180",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        -199.0,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseLongitudeIsMoreThan180(){
        Assert.assertFalse(
                "add new airport should be false / longitude > 180",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        365.0,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 0",
                0,
                airportRepository.count())
    }

    @Test
    fun shouldBeTrueBecauseLatitudeIsBetweenMinus90And90AndLongitudeIsBetweenMinus180And180(){
        Assert.assertTrue(
                "should be true / longitude = 180, latitude = 90",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        90.0,
                        180.0,
                        "apOwner1")))
        Assert.assertEquals(
                "should be 1",
                1,
                airportRepository.count())
    }

    @Test
    fun shouldBeTrueBecauseLatitudeIs90AndLongitudeIs180(){
        Assert.assertTrue(
                "should be true / longitude = 180, latitude = 90",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        90.0,
                        180.0,
                        "apOwner1")))
        Assert.assertEquals(
                "should be 1",
                1,
                airportRepository.count())
    }
    @Test
    fun shouldBeTrueBecauseLatitudeIsMinus90AndLongitudeIsMinus180() {
        Assert.assertTrue(
                "should be true / longitude = -180, latitude = -90",
                airportCreator.create(AirportNewDto(
                        "newAirport123",
                        50,
                        -90.0,
                        -180.0,
                        "apOwner2")))
        Assert.assertEquals(
                "should be 1",
                1,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseAirportNameActuallyExists(){
        Assert.assertTrue(
                "should be true / new Airport",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        30.0,
                        "apOwner1")))
        Assert.assertEquals(
                "should be 1",
                1,
                airportRepository.count())
        Assert.assertFalse(
                "should be false / name already exists",
                airportCreator.create(AirportNewDto(
                        "newAirport",
                        50,
                        -30.00,
                        50.0,
                        "apOwner2")))
        Assert.assertEquals(
                "should be still 1",
                1,
                airportRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseUserAlreadyHasAirport(){
        Assert.assertTrue(
                "should be true / new airport",
                airportCreator.create(AirportNewDto(
                        "newAirport123",
                        50,
                        -30.00,
                        30.0,
                        "apOwner1")))
        Assert.assertEquals(
                "should be 1",
                1,
                airportRepository.count())
        Assert.assertFalse(
                "should be false / user already has airport",
                airportCreator.create(AirportNewDto(
                        "newAirport3321",
                        50,
                        -30.00,
                        50.0,
                        "apOwner1")))
        Assert.assertEquals(
                "should be still 1",
                1,
                airportRepository.count())
    }
}
