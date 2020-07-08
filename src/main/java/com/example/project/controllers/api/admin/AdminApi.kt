package com.example.project.controllers.api.admin

import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.UserRoleDto
import com.example.project.entity.User
import org.springframework.web.bind.annotation.*

/**
 * Rest Controller obsługujący zapytania pod adres /api/admins
 */
@RestController
@CrossOrigin
@RequestMapping("/api/admins")
class AdminApi(
        private val userApi: UserApi
) {
    /**
     * Dodaje role do listy ról
     * @param userRole UserRoleDto z nazwą użytkownika i nową rolą do dodania
     */
    @PostMapping("/addRole")
    fun changeAdd(@RequestBody userRole: UserRoleDto) {
        val user = userApi.getUserByName(userRole.username) as User
        val roles: MutableList<String> = arrayListOf(user.roles)
        roles.add(userRole.role)
        user.roles = roles.joinToString(separator = ",")
        userApi.updateUser(user)
    }

    /**
     * Usuwa rolę z listy ról
     * @param userRole UserRoleDto z nazwą użytkownika i rolą, jaka ma być usunięta
     */
    @PostMapping("/removeRole")
    fun changeRemove(@RequestBody userRole: UserRoleDto) {
        val user = userApi.getUserByName(userRole.username) as User
        val roles: MutableList<String> = arrayListOf(userRole.role)
        roles.remove(userRole.role)
        user.roles = roles.joinToString(separator = ",")
        userApi.updateUser(user)
    }

    /**
     * Zamienia obecne role użytkownika na podaną
     * @param userRole UserRoleDto z nazwą użytkownika i rolą, jaka ma być ustawiona
     */
    @PostMapping("/changeRole")
    fun changeRole(@RequestBody userRole: UserRoleDto) {
        changeRole(userApi.getUserByName(userRole.username) as User, userRole.role)
    }

    /**
     * Wykorzystywana przez <code>changeRole()</code>
     * @param user Użytkownik, którego rola ma być zmieniona
     * @param role USER, ADMIN, AIRLINE_OWNER lub AIRPORT_OWNER
     */
    fun changeRole(user: User, role: String) {
        user.roles = role
        userApi.updateUser(user)
    }
}
