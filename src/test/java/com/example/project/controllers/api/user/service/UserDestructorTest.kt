package com.example.project.controllers.api.user.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.airline.AirlineApi
import com.example.project.controllers.api.airport.AirportApi
import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.AirlineNewDto
import com.example.project.dto.AirportNewDto
import com.example.project.dto.UserChangeDto
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class UserDestructorTest @Autowired constructor(
        private val userApi: UserApi,
        private val userDestructor: UserDestructor,
        private val userCreator: UserCreator,
        private val adminApi: AdminApi,
        private val airportApi: AirportApi,
        private val airlineApi: AirlineApi
) {

    @BeforeEach
    fun setUp() {
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "user",
                        "password",
                        "email@gmail.com"
                )), "USER")
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "user2",
                        "password",
                        "email@super.com"
                )), "USER")
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "user3",
                        "password",
                        "email@emal.pl"
                )), "USER")
    }

    @AfterEach
    internal fun tearDown() {
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldBeOk() {
        Assert.assertTrue(
                "should be true / database should be not empty",
                userApi.getAllUsersDto().isNotEmpty())
        Assert.assertTrue(
                "should be true / all users should be removed",
                userDestructor.deleteAllUser())
        Assert.assertTrue(
                "should be true / database should be empty now",
                userApi.getAllUsersDto().isEmpty())
    }

    @Test
    fun shouldBeTrueEvenIfDatabaseIsEmpty() {
        Assert.assertTrue(
                "should be true / database should be not empty",
                userApi.getAllUsersDto().isNotEmpty())
        Assert.assertTrue(
                "should be true / all users should be removed",
                userDestructor.deleteAllUser())
        Assert.assertTrue(
                "should retun true even if database is empty",
                userDestructor.deleteAllUser())
        Assert.assertTrue(
                "should be true, database should be empty",
                userApi.getAllUsersDto().isEmpty())
    }

    @Test
    fun shouldDeleteOneUser() {
        Assert.assertNotNull(
                "array should be not null",
                userApi.getUserByName("user"))
        Assert.assertTrue(
                "should be true / user should be removed",
                userDestructor.deleteUserByUsername("user"))
        Assert.assertNull(
                "array should be null",
                userApi.getUserByName("user"))
    }

    @Test
    fun shouldRemoveUserEvenIfUserIsAirportOwner() {
        val user = "airportOwner"
        val airport = "airport"
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        user,
                        "password",
                        "airportOwner@emal.pl"
                )), "AIRPORT_OWNER")
        airportApi.postNewAirport(AirportNewDto(
                airport,
                50,
                134.5,
                5.431,
                user
        ))

        Assert.assertTrue(
                "should be true / user should be removed",
                userDestructor.deleteUserByUsername(user))
        Assert.assertNull(
                "should be null / user is removed",
                userApi.getUserByName(user))
        Assert.assertNull(
                "should be null / airport is also removed",
                airportApi.getAirportByName(airport))
    }

    @Test
    fun shouldRemoveUserEvenIfUserIsLineOwner() {
        val user = "airlineOwner"
        val airline = "airline"
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        user,
                        "password",
                        "airlineOwner@emal.pl"
                )), "AIRLINE_OWNER")
        airlineApi.postNewAirline(AirlineNewDto(airline, user))

        Assert.assertTrue(
                "should be true / user should be removed",
                userDestructor.deleteUserByUsername(user))
        Assert.assertNull(
                "should be null / user is removed",
                userApi.getUserByName(user))
        Assert.assertNull(
                "should be null / airline is also removed",
                airlineApi.getAirlineByNameDto(airline))
    }
}
