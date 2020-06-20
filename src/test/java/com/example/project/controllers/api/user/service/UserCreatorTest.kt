package com.example.project.controllers.api.user.service

import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.UserChangeDto
import com.example.project.repository.UserRepository
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureTestDatabase
internal class UserCreatorTest @Autowired constructor(
        private val userRepository: UserRepository,
        private val userDestructor: UserDestructor,
        private val userCreator: UserCreator
   //     private val userApi: UserApi
) {
    @BeforeEach
    internal fun setUp() {
        userDestructor.deleteAllUser()

        userCreator.createUser(UserChangeDto("basic",
                "password",
                "basic@gmail.com"))
    }

    @Test
    fun createUserShouldBeCorrect() {
        val user: UserChangeDto = UserChangeDto("newUser",
                "password",
                "email@gmail.com")
        Assert.assertTrue(userCreator.createUser(user))
        Assert.assertTrue(userRepository.existsByLogin("newUser"))
    }

    @Test
    fun createUserEmptyUsername() {
        val user: UserChangeDto = UserChangeDto("",
                "password",
                "email@gmail.com")
        Assert.assertFalse(userCreator.createUser(user))
    }

    @Test
    fun createUserEmptyPassword() {
        val user: UserChangeDto = UserChangeDto("newUser",
                "",
                "email@gmail.com")
        Assert.assertFalse(userCreator.createUser(user))
    }

    @Test
    fun createUserEmptyEmail() {
        val user = UserChangeDto("newUser", "password", "")
        Assert.assertFalse(userCreator.createUser(user))
    }

    @Test
    fun createUserButWrongEmailPattern() {
        val user = UserChangeDto("user123", "Password", "wrongemail")
        Assert.assertFalse(userCreator.createUser(user))
    }

    @Test
    fun createUserButUserExist() {
        val user2 = UserChangeDto("basic", "password1", "email2@gmail.com")
        Assert.assertFalse("username", userCreator.createUser(user2))
        val user3 = UserChangeDto("newUserotherLogin", "password1", "basic@gmail.com")
        Assert.assertFalse("email", userCreator.createUser(user3))
    }
}
