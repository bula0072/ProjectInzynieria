package com.example.project.controllers.api.admin

import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.UserRoleDto
import com.example.project.entity.User
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/admins")
class AdminApi(
        private val userApi: UserApi
) {
    @GetMapping("/test")
    fun test() = "admin stuff"

    @PostMapping("/addRole")
    fun changeAdd(@RequestBody userRole: UserRoleDto) {
        val user = userApi.getUserByName(userRole.username) as User
        val roles: MutableList<String> = arrayListOf(user.roles)
        roles.add(userRole.role)
        user.roles = roles.joinToString(separator = ",")
        userApi.updateUser(user)
    }

    @PostMapping("/removeRole")
    fun changeRemove(@RequestBody userRole: UserRoleDto) {
        val user = userApi.getUserByName(userRole.username) as User
        val roles: MutableList<String> = arrayListOf(userRole.role)
        roles.remove(userRole.role)
        user.roles = roles.joinToString(separator = ",")
        userApi.updateUser(user)
    }

    @PostMapping("/changeRole")
    fun changeRole(@RequestBody userRole: UserRoleDto) {
        changeRole(userApi.getUserByName(userRole.username) as User, userRole.role)
    }

    fun changeRole(user: User, role: String) {
        user.roles = role
        userApi.updateUser(user)
    }
}
