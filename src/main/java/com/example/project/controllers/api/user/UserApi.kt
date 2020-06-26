package com.example.project.controllers.api.user

import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.controllers.api.user.service.UserEditor
import com.example.project.dto.UserBasicDto
import com.example.project.dto.UserChangeDto
import com.example.project.entity.User
import com.example.project.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/users")
class UserApi(
        private val userRepository: UserRepository,
        private val userCreator: UserCreator,
        private val userDestructor: UserDestructor,
        private val userEditor: UserEditor // brak konstruktorów
) {
    @GetMapping
    fun getAllUsersDto() = userRepository
            .findAll()
            .map { t -> UserBasicDto(t) }

    @GetMapping("/{username}")
    fun getUserByNameDto(@PathVariable(name = "username") username: String) =
            getUserByName(username)?.let { UserBasicDto(it) }

    @DeleteMapping("/{username}")
    fun deleteUser(@PathVariable(name = "username") username: String) =
            userDestructor.deleteUserByUsername(username)

    @PatchMapping("/{username}")
    fun patchUser(@PathVariable(name = "username") username: String,
                  @RequestBody userChange: UserChangeDto) =
            userEditor.editUser(username, userChange)


    @PostMapping
    fun postNewUser(@RequestBody newUser: UserChangeDto) =
            userCreator.createUser(newUser)

    fun getUserByName(username: String?): User? =
            userRepository.findByLogin(username)

    fun updateUser(user: User) {
        userRepository.save(user)
    }
}
