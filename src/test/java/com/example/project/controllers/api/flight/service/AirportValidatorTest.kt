package com.example.project.controllers.api.flight.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.airline.service.AirlineCreator
import com.example.project.controllers.api.airline.service.AirlineDestructor
import com.example.project.controllers.api.airplane.service.AirplaneCreator
import com.example.project.controllers.api.airplane.service.AirplaneDestructor
import com.example.project.controllers.api.airport.service.AirportCreator
import com.example.project.controllers.api.airport.service.AirportDestructor
import com.example.project.controllers.api.flight.FlightApi
import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.dto.*
import com.example.project.repository.FlightRepository
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class AirportValidatorTest @Autowired constructor(
        private val adminApi: AdminApi,
        private val userCreator: UserCreator,
        private val userDestructor: UserDestructor,
        private val airportDestructor: AirportDestructor,
        private val airportCreator: AirportCreator,
        private val airlineCreator: AirlineCreator,
        private val airlineDestructor: AirlineDestructor,
        private val airplaneCreator: AirplaneCreator,
        private val airplaneDestructor: AirplaneDestructor,
        private val flightApi: FlightApi,
        private val flightRepository: FlightRepository,
        private val airportValidator: AirportValidator
) {
    private var plane1: Long = 0
    private var plane2: Long = 0
    private var plane3: Long = 0

    @BeforeEach
    fun setUp() {
        //region airport owner 1 = apOwner1
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "apOwner1",
                        "password",
                        "ap1@gmail.com"
                )), "AIRPORT_OWNER")
        //endregion
        //region airport owner 2 = apOwner2
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "apOwner2",
                        "password",
                        "ap2@gmail.com"
                )), "AIRPORT_OWNER")

        //endregion
        //region airline owner = alOwner1
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "alOwner1",
                        "password",
                        "al1@gmail.com"
                )), "AIRLINE_OWNER")
        airlineCreator.create(
                AirlineNewDto(
                        "airline",
                        "alOwner1"
                )
        )
        //endregion
        //region planes = airplane1, airplane2, airplane3
        plane1 = (airplaneCreator.creator(
                AirplaneNewDto(
                        "airplane1",
                        50,
                        50000.0,
                        "alOwner1"
                )
        )).id
        plane2 = (airplaneCreator.creator(
                AirplaneNewDto(
                        "airplane2",
                        50,
                        50000.0,
                        "alOwner1"
                )
        )).id
        plane3 = (airplaneCreator.creator(
                AirplaneNewDto(
                        "airplane3",
                        50,
                        50000.0,
                        "alOwner1"
                )
        )).id
        //endregion
        //region airports = airport1, airport2
        airportCreator.create(
                AirportNewDto(
                        "airport1",
                        3,
                        50.0,
                        50.0,
                        "apOwner1"
                )
        )
        airportCreator.create(
                AirportNewDto(
                        "airport2",
                        3,
                        50.0,
                        50.0,
                        "apOwner2"
                )
        )
        //endregion
    }

    @AfterEach
    internal fun tearDown() {
        flightRepository.deleteAll()
        userDestructor.deleteAllUser()
        airlineDestructor.deleteAll()
        airplaneDestructor.deleteAll()
        airportDestructor.deleteAll()
    }

    @Test
    fun airportValidatorChouldBeOk() {
        Assert.assertTrue(
                "Should be ok, there is no flight / plane1",
                airportValidator.validate(
                        flightGenerator("15:00", "20:30", plane1)
                )
        )
        flightSender("15:00", "20:30", plane1)
        Assert.assertTrue(
                "Should be ok / start < 14:30 / 15:30 < end < 20:00",
                airportValidator.validate(
                        flightGenerator("14:15", "15:50", plane1)
                )
        )
        Assert.assertTrue(
                "Should be ok / 15:30 < start  < end < 20:00",
                airportValidator.validate(
                        flightGenerator("15:55", "16:30", plane1)
                )
        )
        Assert.assertTrue(
                "Should be ok / 15:30 < start < 20:00 < 21:00 < end",
                airportValidator.validate(
                        flightGenerator("15:55", "22:12", plane1)
                )
        )
        Assert.assertTrue(
                "Should be ok / 15:30  < 21:00 < start < end",
                airportValidator.validate(
                        flightGenerator("21:10", "23:12", plane1)
                )
        )
    }

    @Test
    fun airportValidatorButDatePatternIsWrong() {
        Assert.assertTrue(
                "should be ok",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T15:00:00.00Z", "2020-01-01T20:30:00.00Z", plane1)

                )
        )
        Assert.assertFalse(
                "Empty start time",
                airportValidator.validate(
                        flightGeneratorFullTime("", "2020-01-01T20:30:00.00Z", plane1)
                )
        )
        Assert.assertFalse(
                "Empty end time",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T20:30:00.00Z", "", plane1)
                )
        )
        Assert.assertFalse(
                "Null start time",
                airportValidator.validate(
                        flightGeneratorFullTime(null, "2020-01-01T20:30:00.00Z", plane1)
                )
        )
        Assert.assertFalse(
                "Null end time",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T20:30:00.00Z", null, plane1)
                )
        )
        Assert.assertFalse(
                "Wrong start time pattern",
                airportValidator.validate(
                        flightGeneratorFullTime("wrong pattern", "2020-01-01T20:30:00.00Z", plane1)
                )
        )
        Assert.assertFalse(
                "Wrong end time pattern",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T20:30:00.00Z", "another wrong pattern", plane1)
                )
        )
        Assert.assertFalse(
                "Wrong start date -20",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T-20:30:00.00Z", "2020-01-01T21:13:00.00Z", plane1)
                )
        )
        Assert.assertFalse(
                "Wrong start date 20:80",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T20:80:00.00Z", "2020-01-01T21:13:00.00Z", plane1)
                )
        )
        Assert.assertFalse(
                "Wrong end date -20",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T20:30:00.00Z", "2020-01-01T-20:13:00.00Z", plane1)
                )
        )
        Assert.assertFalse(
                "Wrong start date 21:80",
                airportValidator.validate(
                        flightGeneratorFullTime("2020-01-01T20:30:00.00Z", "2020-01-01T21:80:00.00Z", plane1)
                )
        )
    }

    @Test
    fun airportValidatorButDateIsWrong() {
        Assert.assertTrue(
                "should be ok",
                airportValidator.validate(
                        flightGenerator("15:00", "20:30", plane1)
                )
        )
        Assert.assertFalse(
                "start time > end time same day",
                airportValidator.validate(
                        flightGenerator("20:30", "15:12", plane1)
                )
        )
        Assert.assertFalse(
                "start time == end time same day",
                airportValidator.validate(
                        flightGenerator("20:30", "20:30", plane1)
                )
        )
    }

    @Test
    fun airportsAreTheSameShouldBeFalse() {
        Assert.assertTrue(
                "should be ok",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                "airport1",
                                "airport2",
                                plane1
                        )
                )
        )
        Assert.assertFalse(
                "should be false, airports are the same",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                "airport1",
                                "airport1",
                                plane1
                        )
                )
        )
    }

    @Test
    fun airportsAreNull() {
        Assert.assertTrue(
                "should be ok",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                "airport1",
                                "airport2",
                                plane1
                        )
                )
        )
        Assert.assertFalse(
                "should be false / null start airport",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                null,
                                "airport2",
                                plane1
                        )
                )
        )
        Assert.assertFalse(
                "should be false / null end airport",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                "airport1",
                                null,
                                plane1
                        )
                )
        )
    }

    @Test
    fun airportsAreEmpty() {
        Assert.assertTrue(
                "should be ok",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                "airport1",
                                "airport2",
                                plane1
                        )
                )
        )
        Assert.assertFalse(
                "should be false / empty start airport",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                "",
                                "airport2",
                                plane1
                        )
                )
        )
        Assert.assertFalse(
                "should be false / empty end airport",
                airportValidator.validate(
                        FlightNewDto(
                                50.0,
                                "2020-01-01T15:00:00.00Z",
                                "2020-01-01T20:30:00.00Z",
                                "airport1",
                                "",
                                plane1
                        )
                )
        )
    }

    private fun flightSender(startTime: String, endTime: String, apId: Long): Boolean {
        return flightApi.newFlight(flightGenerator(startTime, endTime, apId))
    }

    private fun flightGenerator(startTime: String?, endTime: String?, apId: Long): FlightNewDto {
        return FlightNewDto(
                50.0,
                "2020-01-01T${startTime}:00.00Z",
                "2020-01-01T${endTime}:00.00Z",
                "airport1", "airport2", apId)
    }

    private fun flightGeneratorFullTime(startTime: String?, endTime: String?, apId: Long): FlightNewDto {
        return FlightNewDto(
                50.0,
                startTime,
                endTime,
                "airport1", "airport2", apId)
    }
}
