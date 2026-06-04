package org.example.userservice.controller;

import org.example.userservice.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public UserResponse getUser() {
        return new UserResponse("Sarthak");
    }

}

