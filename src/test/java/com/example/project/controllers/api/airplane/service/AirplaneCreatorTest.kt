package com.example.project.controllers.api.airplane.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.user.UserApi
import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.dto.AirplaneNewDto
import com.example.project.dto.UserChangeDto
import com.example.project.repository.AirplaneRepository
import com.example.project.repository.UserRepository
import org.junit.Assert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class AirplaneCreatorTest @Autowired constructor(
        private val airplaneRepository: AirplaneRepository,
        private val airplaneCreator: AirplaneCreator,
        private val adminApi: AdminApi,
        private val userDestructor: UserDestructor,
        private val userCreator: UserCreator
) {
    @BeforeEach
    internal fun setUp() {
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
    }

    @AfterEach
    internal fun tearDown() {
        airplaneRepository.deleteAll()
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldBeOk() {
        val apCorrect = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "airlineOwner")
        Assert.assertTrue(
                "should be ok",
                airplaneCreator.create(apCorrect))
        Assert.assertEquals(
                "Should be 1 airplanes in database",
                1,

                airplaneRepository.findAll().size)
    }

    @Test
    fun shouldBeFalseBecauseUserHasWrongRole() {
        val apWrongRole = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "user")
        Assert.assertFalse(
                "should be false / wrong role",
                airplaneCreator.create(apWrongRole))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size
        )
    }

    @Test
    fun shouldBeFalseBecauseUserIsEmpty() {
        val apEmptyUser = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "")
        Assert.assertFalse(
                "should be false / user is empty",
                airplaneCreator.create(apEmptyUser))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size
        )
    }

    @Test
    fun shouldBeFalseBecauseUserIsNull() {
        val apEmptyUser = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                null)
        Assert.assertFalse(
                "should be false / user is empty",
                airplaneCreator.create(apEmptyUser))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size
        )
    }

    @Test
    fun shouldBeFalseBecauseUserDoesntExists() {
        val apWrongUser = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "uzytkownikKtoregoNieMa")
        Assert.assertFalse(
                "should be false, user doesnt exists",
                airplaneCreator.create(apWrongUser))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size
        )
    }

    @Test
    fun shouldBeFalseBecauseAirplaneNameIsNull() {
        Assert.assertFalse("should be false / name is null",
                airplaneCreator.create(AirplaneNewDto(
                        null,
                        50,
                        50.50,
                        "airlineOwner")))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size)
    }

    @Test
    fun shouldBeFalseBecauseAirplaneNameIsEmpty(){
        Assert.assertFalse("should be false / name is empty",
                airplaneCreator.create(AirplaneNewDto(
                        "",
                        50,
                        50.50,
                        "airlineOwner")))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size)
    }

    @Test
    fun shouldBeFalseBecauseCapacityIsLessThanZero(){
        Assert.assertFalse("should be false / capacity less than 0",
                airplaneCreator.create(AirplaneNewDto(
                        "nowy",
                        -50,
                        50.50,
                        "airlineOwner")))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size)
    }

    @Test
    fun shouldBeFalseBecauseCapacityIsZero(){
        Assert.assertFalse("should be false / capacity is 0",
                airplaneCreator.create(AirplaneNewDto(
                        "nowy",
                        0,
                        50.50,
                        "airlineOwner")))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size)
    }

    @Test
    fun shouldBeFalseBecauseMaxDistanceIsZero(){
        Assert.assertFalse("should be false / max distance is 0",
                airplaneCreator.create(AirplaneNewDto(
                        "nowy",
                        0,
                        0.0,
                        "airlineOwner")))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size)
    }

    @Test
    fun shouldBeFalseBecauseMaxDistanceIsLessThanZero(){
        Assert.assertFalse("should be false / max distance less than 0",
                airplaneCreator.create(AirplaneNewDto(
                        "nowy",
                        0,
                        -50.0,
                        "airlineOwner")))
        Assert.assertEquals(
                "should be 0 airplanes in database",
                0,
                airplaneRepository.findAll().size)
    }
}
