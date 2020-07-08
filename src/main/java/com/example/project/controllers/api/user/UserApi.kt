package com.example.project.controllers.api.user

import com.example.project.controllers.api.user.service.UserCreator
import com.example.project.controllers.api.user.service.UserDestructor
import com.example.project.controllers.api.user.service.UserEditor
import com.example.project.dto.UserBasicDto
import com.example.project.dto.UserChangeDto
import com.example.project.entity.User
import com.example.project.repository.UserRepository
import org.springframework.web.bind.annotation.*
/**
 * Rest Controller obsługujący zapytania pod adres /api/users
 */
@RestController
@CrossOrigin
@RequestMapping("/api/users")
class UserApi(
        private val userRepository: UserRepository,
        private val userCreator: UserCreator,
        private val userDestructor: UserDestructor,
        private val userEditor: UserEditor
) {
    /**
     * Pobranie listy wszystkich użytkowników
     * @return List<UserBasicDto>
     */
    @GetMapping
    fun getAllUsersDto() = userRepository
            .findAll()
            .map { t -> UserBasicDto(t) }

    /**
     * Pobranie danych użytkownika
     * @param username nazwa użytkownika
     * @return UserBasicDto
     */
    @GetMapping("/{username}")
    fun getUserByNameDto(@PathVariable(name = "username") username: String) =
            getUserByName(username)?.let { UserBasicDto(it) }

    /**
     * Usunięcie użytkownika z bazy danych
     * @param username nazwa użytkownika
     * @return true jeżeli użytkownik został poprawnie usunięty
     */
    @DeleteMapping("/{username}")
    fun deleteUser(@PathVariable(name = "username") username: String) =
            userDestructor.deleteUserByUsername(username)

    /**
     * Edycja danych użytkownika
     * @param username stara nazwa użytkownika
     * @param userChange nowe dane użytkownika
     * @return true jeżeli dane użytkownika zostały porawenie zmienione
     */
    @PatchMapping("/{username}")
    fun patchUser(@PathVariable(name = "username") username: String,
                  @RequestBody userChange: UserChangeDto) =
            userEditor.editUser(username, userChange)

    /**
     * Dodanie nowego użytkownika
     * @param newUser dane nowego użytkownika
     * @return true, jeżeli użytkownik został dodany
     */
    @PostMapping
    fun postNewUser(@RequestBody newUser: UserChangeDto) =
            userCreator.createUser(newUser)

    /**
     * Pobranie danych użytkownika
     * @param username nazwa użytkownika
     * @return User
     */
    fun getUserByName(username: String?): User? =
            userRepository.findByLogin(username)

    /**
     * Zmiana danych użytkownika
     * @param user użytkownik ze zmienionymi parametrami
     */
    fun updateUser(user: User) {
        userRepository.save(user)
    }
}
