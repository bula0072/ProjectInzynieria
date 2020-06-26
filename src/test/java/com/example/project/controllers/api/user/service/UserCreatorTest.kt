package com.example.project.controllers.api.user.service

import com.example.project.dto.UserChangeDto
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
internal class UserCreatorTest @Autowired constructor(
        private val userRepository: UserRepository,
        private val userDestructor: UserDestructor,
        private val userCreator: UserCreator
) {
    @BeforeEach
    internal fun setUp() {
        userCreator.createUser(UserChangeDto("basic",
                "password",
                "basic@gmail.com"))
    }

    @AfterEach
    internal fun tearDown() {
        userDestructor.deleteAllUser()
    }

    @Test
    fun shouldBeCorrect() {
        val user: UserChangeDto = UserChangeDto("newUser",
                "password",
                "email@gmail.com")
        Assert.assertTrue(
                "should be true / new user should be in database",
                userCreator.createUser(user))
        Assert.assertTrue(
                "should be true",
                userRepository.existsByLogin("newUser"))
    }

    @Test
    fun shouldBeFalseBecauseUsernameIsEmpty() {
        val user: UserChangeDto = UserChangeDto("",
                "password",
                "email@gmail.com")
        Assert.assertFalse(
                "should be false / empty username",
                userCreator.createUser(user))
    }

    @Test
    fun shouldBeFalseBecauseUsernameIsNull() {
        val user: UserChangeDto = UserChangeDto(null,
                "password",
                "email@gmail.com")
        Assert.assertFalse(
                "should be false / null username",
                userCreator.createUser(user))
    }

    @Test
    fun shouldBeFalseBecausePasswordIsEmpty() {
        val user: UserChangeDto = UserChangeDto("newUser",
                "",
                "email@gmail.com")
        Assert.assertFalse(
                "should be false / empty password",
                userCreator.createUser(user))
    }

    @Test
    fun shouldBeFalseBecausePasswordIsNull() {
        val user: UserChangeDto = UserChangeDto("newUser",
                null,
                "email@gmail.com")
        Assert.assertFalse(
                "should be false / null password",
                userCreator.createUser(user))
    }

    @Test
    fun shouldBeFalseBecauseEmailIsEmpty() {
        val user = UserChangeDto("newUser", "password", "")
        Assert.assertFalse(
                "should be false / empty email",
                userCreator.createUser(user))
    }

    @Test
    fun shouldBeFalseBecauseEmailIsNull() {
        val user = UserChangeDto("newUser", "password", null)
        Assert.assertFalse(
                "should be false / null email",
                userCreator.createUser(user))
    }

    @Test
    fun shouldBeFalseBecauseEmailPatternIsWrong() {
        val user = UserChangeDto("user123", "Password", "wrongemail")
        Assert.assertFalse(
                "should be false / email pattern not match",
                userCreator.createUser(user))
    }

    @Test
    fun shouldBeFalseBecauseUsernameAlreadyExists() {
        val user2 = UserChangeDto("basic", "password1", "email2@gmail.com")
        Assert.assertFalse(
                "should be false / username already exists",
                userCreator.createUser(user2))
    }

    @Test
    fun shouldBeFalseBecauseEmailAlreadyExists() {

        val user3 = UserChangeDto("newUserotherLogin", "password1", "basic@gmail.com")
        Assert.assertFalse(
                "should be false / email already exists",
                userCreator.createUser(user3))
    }
}
