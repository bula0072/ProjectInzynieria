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
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
@AutoConfigureTestDatabase
internal class UserEditorTest @Autowired constructor(
        private val userCreator: UserCreator,
        private val userDestructor: UserDestructor,
        private val userEditor: UserEditor,
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {

    @BeforeEach
    fun setUp() {
        userCreator.createUser(UserChangeDto("basic",
                "password",
                "basic@gmail.com"))
    }

    @AfterEach
    internal fun tearDown() {
        userDestructor.deleteAllUser()
    }

    @Test
    fun editUserShouldBeOk() {
        val oldUsername = "basic"
        val newUsername = "newName"
        val newPassword = "newPassword"
        val newEmail = "this@is.email"
        Assert.assertTrue("edit username should return true", userEditor
                .editUser(
                        oldUsername,
                        UserChangeDto(
                                newUsername,
                                null,
                                null)))
        Assert.assertTrue("edit password should return true", userEditor
                .editUser(
                        newUsername,
                        UserChangeDto(
                                null,
                                newPassword,
                                null)))
        Assert.assertTrue("edit email should return true", userEditor
                .editUser(
                        newUsername,
                        UserChangeDto(
                                null,
                                null,
                                newEmail)))

        val user = userRepository.findAll()[0]
        Assert.assertEquals("usernames should be equal", newUsername, user.login)
        Assert.assertEquals("email should be equal", newEmail, user.email)
    }

    @Test
    fun editUserButWithEmptyValues() {
        val username = "basic"
        Assert.assertTrue("edit username should return true", userEditor
                .editUser(
                        username,
                        UserChangeDto(
                                "",
                                null,
                                null)))
        Assert.assertTrue("edit password should return true", userEditor
                .editUser(
                        username,
                        UserChangeDto(
                                null,
                                "",
                                null)))
        Assert.assertTrue("edit email should return true", userEditor
                .editUser(
                        username,
                        UserChangeDto(
                                null,
                                null,
                                "")))
    }

    @Test
    fun editUserNewValuesExactlyLikePreviousValuesShouldReturnTrue() {
        val user = userRepository.findByLogin("basic")
        Assert.assertTrue("edit username should return true", userEditor
                .editUser(
                        user.login,
                        UserChangeDto(
                                user.login,
                                null,
                                null)))
        Assert.assertTrue("edit password should return true", userEditor
                .editUser(
                        user.login,
                        UserChangeDto(
                                null,
                                user.password,
                                null)))
        Assert.assertTrue("edit email should return true", userEditor
                .editUser(
                        user.login,
                        UserChangeDto(
                                null,
                                null,
                                user.email)))
    }

    @Test
    fun editUserButValuesExistsInDatabase() {
        val newUser = "newUser"
        userCreator.createUser(UserChangeDto(newUser,
                "password",
                "newUser@gmail.com"))

        Assert.assertFalse("edit username should return false", userEditor
                .editUser(
                        newUser,
                        UserChangeDto(
                                "basic",
                                null,
                                null)))

        Assert.assertFalse("edit email should return false", userEditor
                .editUser(
                        newUser,
                        UserChangeDto(
                                null,
                                null,
                                "basic@gmail.com"
                        )))
    }

    @Test
    fun editUserWrongNewEmail() {
        Assert.assertFalse("wrong email pattern", userEditor
                .editUser(
                        "basic",
                        UserChangeDto(
                                null,
                                null,
                                "zlyemail"
                        )
                ))
    }
}
