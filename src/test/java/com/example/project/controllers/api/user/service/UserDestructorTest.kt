package com.example.project.controllers.api.user.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.airline.AirlineApi
import com.example.project.controllers.api.airport.AirportApi
import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.AirlineNewDto
import com.example.project.dto.AirportNewDto
import com.example.project.dto.UserChangeDto
import org.junit.Assert
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
        userDestructor.deleteAllUser()



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

    @Test
    fun deleteAllUserShouldBeCorrect() {
        Assert.assertTrue(userApi.getAllUsersDto().isNotEmpty())
        Assert.assertTrue(userDestructor.deleteAllUser())
        Assert.assertTrue(userApi.getAllUsersDto().isEmpty())
    }

    @Test
    fun deleteAllUserIfDatabaseIsActuallyEmpty(){
        Assert.assertTrue(userApi.getAllUsersDto().isNotEmpty())
        Assert.assertTrue(userDestructor.deleteAllUser())
        Assert.assertTrue(userDestructor.deleteAllUser())
        Assert.assertTrue(userApi.getAllUsersDto().isEmpty())
    }

    @Test
    fun deleteUserByUsernameShouldBeCorrect() {
        Assert.assertNotNull("table should be not null", userApi.getUserByName("user"))
        Assert.assertTrue("delete user should be true",userDestructor.deleteUserByUsername("user"))
        Assert.assertNull("table should be null", userApi.getUserByName("user"))
    }

    @Test
    fun deleteUserByUsernameWhereUserIsAirportOwner(){
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
        Assert.assertTrue("delete user should be true", userDestructor.deleteUserByUsername(user))
        Assert.assertNull("user should be null", userApi.getUserByName(user))
        Assert.assertNull("airport should be null", airportApi.getAirportByName(airport))
    }

    @Test
    fun deleteUserByUsernameWhereUserIsAirlineOwner(){
        val user = "airlineOwner"
        val airline = "airline"
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        user,
                        "password",
                        "airlineOwner@emal.pl"
                )), "AIRLINE_OWNER")
        airlineApi.postNewAirline(AirlineNewDto(airline, user))
        Assert.assertTrue("deleteuser should be true", userDestructor.deleteUserByUsername(user))
        Assert.assertNull("user should be null", userApi.getUserByName(user))
        Assert.assertNull("airline should be null", airlineApi.getAirlineByNameDto(airline))
    }


}
