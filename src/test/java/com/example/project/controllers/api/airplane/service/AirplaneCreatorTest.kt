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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class AirplaneCreatorTest @Autowired constructor(
        private val userRepository: UserRepository,
        private val userApi: UserApi,
        private val airplaneRepository: AirplaneRepository,
        private val airplaneCreator: AirplaneCreator,
        private val adminApi: AdminApi,
        private val userDestructor: UserDestructor,
        private val userCreator: UserCreator
) {
    @BeforeEach
    internal fun setUp() {
        airplaneRepository.deleteAll()
        userDestructor.deleteAllUser()

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

    @Test
    fun createAirplaneIsAirlineOwner() {
        val apCorrect = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "airlineOwner")
        Assert.assertTrue(airplaneCreator.create(apCorrect, userApi))
    }

    @Test
    fun createAirplaneIsNotAirlineOwner() {
        val apWrongRole = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "user")
        Assert.assertFalse(airplaneCreator.create(apWrongRole, userApi))
    }

    @Test
    fun createAirplaneEmptyUser() {
        val apEmptyUser = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "")
        Assert.assertFalse(airplaneCreator.create(apEmptyUser, userApi))
    }

    @Test
    fun createAirplaneThereIsNoThatUser() {
        val apWrongUser = AirplaneNewDto(
                "nowy",
                50,
                50.50,
                "uzytkownikKtoregoNieMa")
        Assert.assertFalse(airplaneCreator.create(apWrongUser, userApi))
    }


    @Test
    fun creatAirplaneWithWrongParameters() {
        val apEmptyName = AirplaneNewDto(
                "",
                50,
                50.50,
                "airlineOwner")
        Assert.assertFalse("Zła nazwa",
                airplaneCreator.create(apEmptyName, userApi))
        val apWrongCapacity = AirplaneNewDto(
                "nowy",
                -50,
                50.50,
                "airlineOwner")
        Assert.assertFalse(
                "Złe capacity",
                airplaneCreator.create(apWrongCapacity, userApi))
        val apWrongMaxDistance = AirplaneNewDto(
                "nowy",
                50,
                0.0,
                "airlineOwner")
        Assert.assertFalse(
                "Zły max distance",
                airplaneCreator.create(apWrongMaxDistance, userApi))
    }
}
