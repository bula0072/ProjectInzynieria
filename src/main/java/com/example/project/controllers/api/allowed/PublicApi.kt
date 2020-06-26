package com.example.project.controllers.api.allowed

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/public")
@CrossOrigin
class PublicApi() {
    @GetMapping("/test")
    fun test() = "{\"test\" : \"public test\"}"
}
