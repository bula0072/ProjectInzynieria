package com.example.project.controllers.api.airline.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.dto.AirlineChangeDto
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
internal class AirlineEditorTest @Autowired constructor(
        private val airlineRepository: AirlineRepository,
        private val userDestructor: UserDestructor,
        private val adminApi: AdminApi,
        private val userCreator: UserCreator,
        private val airlineCreator: AirlineCreator,
        private val airlineEditor: AirlineEditor
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
    fun shouldEditName() {
        Assert.assertTrue(
                "should change name",
                airlineEditor.edit(
                        "AirlineName",
                        AirlineChangeDto("NewAirlineName")))
        Assert.assertTrue(
                "should be true",
                airlineRepository.existsByName("NewAirlineName"))
        Assert.assertFalse(
                "should be false because this airline has new name",
                airlineRepository.existsByName("AirlineName")
        )
    }

    @Test
    fun shouldBeFalseBecauseNewAirlineNameIsNull() {
        Assert.assertFalse(
                "should be false / name is null",
                airlineEditor.edit(
                        "AirlineName",
                        AirlineChangeDto(null)))
    }

    @Test
    fun shouldBeFalseBecauseNewAirlineNameIsEmpty() {
        Assert.assertFalse(
                "should be false / name is empty",
                airlineEditor.edit(
                        "AirlineName",
                        AirlineChangeDto("")))
    }

    @Test
    fun shouldBeFalseBecauseAirlineWithPreviousNameDoesntExists() {
        Assert.assertFalse(
                "should be false / airline not exist",
                airlineEditor.edit(
                        "AirlineFake",
                        AirlineChangeDto("newName")))
    }

    @Test
    fun shouldBeFalseBecauseAirlinePreviousNameIsNull() {
        Assert.assertFalse(
                "should be false / airline is null",
                airlineEditor.edit(
                        null,
                        AirlineChangeDto("newName")))
    }

    @Test
    fun shouldBeFalseBecauseAirlinePrevioisNameIsEmpty() {
        Assert.assertFalse(
                "should be false / airline is empty",
                airlineEditor.edit(
                        "",
                        AirlineChangeDto("newName")))
    }

    @Test
    fun shouldBeFalseBecauseNewAirlineNameAlreadyExistsInDatabase() {
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "airlineOwner2",
                        "password",
                        "email22@gmail.com"
                )), "AIRLINE_OWNER")
        airlineCreator.create(AirlineNewDto(
                "AirlineName2",
                "airlineOwner2"))

        Assert.assertFalse(
                "should be false / airline name already exist",
                airlineEditor.edit(
                        "AirlineName2",
                        AirlineChangeDto("AirlineName")
                )
        )
    }
}
