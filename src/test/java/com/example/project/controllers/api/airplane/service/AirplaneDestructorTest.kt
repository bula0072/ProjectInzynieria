package com.example.project.controllers.api.airplane.service

import com.example.project.controllers.api.admin.AdminApi
import com.example.project.controllers.api.user.UserApi
import com.example.project.controllers.api.user.service.UserCreator
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
internal class AirplaneDestructorTest @Autowired constructor(
        private val userApi: UserApi,
        private val airplaneRepository: AirplaneRepository,
        private val userRepository: UserRepository,
        private val airplaneDestructor: AirplaneDestructor,
        private val adminApi: AdminApi,
        private val airplaneCreator: AirplaneCreator,
        private val userCreator: UserCreator
) {
    var airplaneId: Long = 0

    @BeforeEach
    fun setUp() {
        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "user",
                        "password",
                        "email@test.com"
                )), "USER")

        adminApi.changeRole(userCreator.create(
                UserChangeDto(
                        "airlineOwner",
                        "password",
                        "email@tojest.email"
                )), "AIRLINE_OWNER")

        airplaneId = airplaneCreator.creator(AirplaneNewDto("nowy", 50, 50.50, "airlineOwner")).id
    }

    @AfterEach
    internal fun tearDown() {
        airplaneRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun shouldBeOk() {
        Assert.assertEquals(
                "should be 1 airplanes in database",
                1,
                airplaneRepository.count()
        )
        Assert.assertTrue(
                "should be true",
                airplaneDestructor.delete(airplaneId))
        Assert.assertEquals(
                "Should be 0 airplanes in database",
                0,
                airplaneRepository.count()
        )
    }

    @Test
    fun shouldBeFalseBecauseAirplaneWithThatIdDoesntExists() {
        Assert.assertFalse(
                "should be false / id doesnt exists",
                airplaneDestructor.delete(airplaneId + 1)
        )
        Assert.assertEquals(
                "Should be still 1 airplanes in database",
                1,
                airplaneRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseAirplaneIdIsLessThanZero() {
        Assert.assertFalse(
                "should be false / id is less than 0",
                airplaneDestructor.delete(-1))
        Assert.assertEquals(
                "Should be still 1 airplanes in database",
                1,
                airplaneRepository.count())
    }

    @Test
    fun shouldBeFalseBecauseAirplaneIdIsNull() {
        Assert.assertFalse(
                "should be false / id is less than 0",
                airplaneDestructor.delete(null))
        Assert.assertEquals(
                "Should be still 1 airplanes in database",
                1,
                airplaneRepository.count())
    }
}
