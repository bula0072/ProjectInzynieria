package com.example.project.controllers.api.airport.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.dto.AirportChangeDto
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
internal class AirportEditorTest @Autowired constructor(
        private val airportDestructor: AirportDestructor,
        private val userDestructor: UserDestructor,
        private val adminApi: AdminApi,
        private val userCreator: UserCreator,
        private val airportEditor: AirportEditor,
        private val airportCreator: AirportCreator
) {

    @BeforeEach
    fun setUp() {
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "apo1",
                        "password",
                        "ap1@gmail.com"
                )), "AIRPORT_OWNER")
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "apo2",
                        "password",
                        "ap2@gmail.com"
                )), "AIRPORT_OWNER")
        airportCreator.create(AirportNewDto(
                "aPort1",
                54,
                65.00,
                3.0,
                "apo1"))
        airportCreator.create(AirportNewDto(
                "aPort2",
                54,
                65.00,
                3.0,
                "apo2"))
    }

    @AfterEach
    internal fun tearDown() {
        airportDestructor.deleteAll()
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldBeOk() {
        Assert.assertTrue(
                "should be ok / acitve now should be false",
                airportEditor.setActivity("aPort1"))
        Assert.assertTrue(
                "should be true / name change",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                "newName",
                                null,
                                null,
                                null)))
        Assert.assertTrue(
                "should be true / capacity change",
                airportEditor.edit(
                        "newName",
                        AirportChangeDto(
                                null,
                                4,
                                null,
                                null)))
        Assert.assertTrue(
                "should be true / latitude change",
                airportEditor.edit(
                        "newName",
                        AirportChangeDto(
                                null,
                                null,
                                20.0,
                                null)))
        Assert.assertTrue(
                "should be true / longitude change",
                airportEditor.edit(
                        "newName",
                        AirportChangeDto(
                                null,
                                null,
                                null,
                                20.0)))
        Assert.assertTrue(
                "should be true / activie now should be true",
                airportEditor.setActivity("newName"))
    }

    @Test
    fun shouldBeFalseBecauseAirportIsActive(){
        Assert.assertFalse(
                "should be false / airport is still active",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                "newName",
                                null,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseAirportWithThisNameDoesntExists(){
        Assert.assertFalse(
                "should be false / wrong name",
                airportEditor.setActivity("fakeAirport"))
        Assert.assertFalse(
                "name edit should be false / wrong name",
                airportEditor.edit(
                        "fakeAirport",
                        AirportChangeDto(
                                "newName",
                                null,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseAirportNameIsEmpty(){
        Assert.assertFalse(
                "should be false / empty name",
                airportEditor.setActivity(""))
        Assert.assertFalse(
                "should be false / empty name",
                airportEditor.edit(
                        "",
                        AirportChangeDto(
                                "newName",
                                null,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseNameIsNull(){
        Assert.assertFalse(
                "should be false / null name",
                airportEditor.setActivity(null))
        Assert.assertFalse(
                "should be false / null name",
                airportEditor.edit(
                        null,
                        AirportChangeDto(
                                "newName",
                                null,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseNewNameAlreadyExists(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / new name already exists",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                "aPort2",
                                null,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseNewNameIsEmpty(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / empty new name",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                "",
                                null,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseNewCapacityIsZero(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / capacity = 0",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                0,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseCapacityIsLessThanZero(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / capacity less than 0",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                -5,
                                null,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseLatitudeIsLessThanMinus90(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / latitude is less than -90",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                -95.0,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseLatitudeIsMoreThan90(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / latitude more than 90",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                91.0,
                                null)))
    }

    @Test
    fun shouldBeFalseBecauseLongitudeIsLessThanMinus180(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / longitude < -180",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                null,
                                181.0)))
    }

    @Test
    fun shouldBeFalseBecauseLongitudeIsMoreThan180(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertFalse(
                "should be false / latitude > 180",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                null,
                                181.0)))
    }

    @Test
    fun shouldBeTrueBecauseLatitudeIs90(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertTrue(
                "should be true / latitude is now 90",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                90.0,
                                null)))
    }

    @Test
    fun shouldBeTrueBecauseLatitudeIsMinus90(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertTrue(
                "should be true / latitude is now -90",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                -90.0,
                                null)))
    }

    @Test
    fun shouldBeTrueBecauseLongitudeIs180(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertTrue(
                "should be true / longitude is now -180",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                null,
                                180.0)))
    }

    @Test
    fun shouldBeTrueBecauseLongitudeIsMinus180(){
        Assert.assertTrue(
                "should be true / activity set to false",
                airportEditor.setActivity("aPort1"))
        Assert.assertTrue(
                "should be true / longitude is now -180",
                airportEditor.edit(
                        "aPort1",
                        AirportChangeDto(
                                null,
                                null,
                                null,
                                -180.0)))
    }

}
