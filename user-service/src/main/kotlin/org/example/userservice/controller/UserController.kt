package org.example.userservice.controller

import org.example.userservice.dto.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/user")
    fun getUser(): UserResponse {
        return UserResponse("Sarthak")
    }

}

