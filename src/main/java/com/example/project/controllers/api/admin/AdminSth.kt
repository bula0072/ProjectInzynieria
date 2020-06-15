package com.example.project.controllers.api.admin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminSth {
    @GetMapping("/api/admins/test")
    fun sth(): String = "admin stuff";
}
