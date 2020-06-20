package com.example.project.controllers.api.allowed

import com.example.project.controllers.api.user.UserApi
import com.example.project.dto.UserLoginFormDto
import com.example.project.repository.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/public")
@CrossOrigin
class PublicApi(private val userRepository: UserRepository,
                private val userApi: UserApi) {
    @GetMapping("/test")
    fun test() = "public test"

    @PostMapping("/login")
    fun login(@RequestBody user: UserLoginFormDto) = userApi.getAllUsersDto()
}
