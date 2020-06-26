package com.example.project.dto

import com.example.project.entity.User

open class UserChangeDto(
        val username: String?,
        val password: String?,
        val email: String?
)

class UserLoginFormDto(var username: String, var password: String)

class UserBasicDto(user: User) {
    val username: String = user.login
    val password: String = user.password
    val email: String = user.email
    val roles: String = user.roles
}

class UserRoleDto(
        val username: String,
        val role: String
)
