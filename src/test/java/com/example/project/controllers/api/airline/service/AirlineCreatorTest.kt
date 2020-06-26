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
internal class AirlineCreatorTest @Autowired constructor(
        private val userCreator: UserCreator,
        private val adminApi: AdminApi,
        private val airlineCreator: AirlineCreator,
        private val airlineRepository: AirlineRepository,
        private val userDestructor: UserDestructor
) {

    @BeforeEach
    fun setUp() {
        userCreator.createUser(UserChangeDto("user",
                "password",
                "user@email.com"))
        adminApi.changeRole(userCreator.create(UserChangeDto("airlineOwner1",
                "password",
                "airlineOwner1@email.com")),
                "AIRLINE_OWNER")
        adminApi.changeRole(userCreator.create(UserChangeDto("airlineOwner2",
                "password",
                "airlineOwner2@email.com")),
                "AIRLINE_OWNER")
    }

    @AfterEach
    internal fun tearDown() {
        airlineRepository.deleteAll()
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldCreateAirline(){
        Assert.assertTrue(
                "airline1 should be ok",
                airlineCreator.create(AirlineNewDto(
                        "airline 1",
                        "airlineOwner1")))
    }

    @Test
    fun afterTransactionShouldBeTwoAirlinesInDatabase() {
        Assert.assertTrue(
                "airline1 should be ok",
                airlineCreator.create(AirlineNewDto(
                        "airline 1",
                        "airlineOwner1")))
        Assert.assertTrue(
                "airline2 should be ok",
                airlineCreator.create(AirlineNewDto(
                        "airline 2",
                        "airlineOwner2")))
        Assert.assertEquals(
                "Should be 2",
                2,
                airlineRepository.findAll().size
        )
    }

    @Test
    fun shouldBeFalseBecauseAirlineNameIsNull(){
        Assert.assertFalse(
                "airline should be false / null name",
                airlineCreator.create(AirlineNewDto(
                        null,
                        "airlineOwner1")))
    }

    @Test
    fun shouldBeFalseBecauseAirlineNameIsEmpty(){
        Assert.assertFalse(
                "airline should be false / empty name",
                airlineCreator.create(AirlineNewDto(
                        "",
                        "airlineOwner1")))
    }

    @Test
    fun shouldBeFalseBecauseUsernameIsEmpty(){
        Assert.assertFalse(
                "airline should be false / empty user",
                airlineCreator.create(AirlineNewDto(
                        "airline",
                        "")))
    }

    @Test
    fun souldBeFalseBecauseUserIsNull() {
        Assert.assertFalse(
                "airline should be false / null user",
                airlineCreator.create(AirlineNewDto(
                        "airline",
                        null)))
    }

    @Test
    fun shouldBeFalseBecauseUserHasWrongRole(){
        Assert.assertFalse(
                "should be false / role is USER",
                airlineCreator.create(AirlineNewDto(
                        "airline",
                        "user")))
    }

    @Test
    fun shouldBeFalseBecauseAirlineOwnerShouldHasOnlyOnweAirline() {
        Assert.assertTrue(
                "should be true",
                airlineCreator.create(AirlineNewDto(
                        "airline",
                        "airlineOwner1")))
        Assert.assertFalse(
                "should be false / airlineOwner duplicate",
                airlineCreator.create(AirlineNewDto(
                        "airline2",
                        "airlineOwner1")))
    }

    @Test
    fun shouldBeFalseBecauseAirlineAlreadyExists() {
        Assert.assertTrue(
                "should be ok",
                airlineCreator.create(AirlineNewDto(
                        "airline",
                        "airlineOwner1")))
        Assert.assertFalse(
                "should be false / airline name already exist",
                airlineCreator.create(AirlineNewDto(
                        "airline",
                        "airlineOwner2")))
    }
}
