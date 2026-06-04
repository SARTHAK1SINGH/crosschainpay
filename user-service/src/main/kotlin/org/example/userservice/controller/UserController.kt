package org.example.userservice.controller

import org.example.userservice.dto.*
import org.example.userservice.security.JwtTokenProvider
import org.example.userservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    /**
     * Register a new user
     * POST /users/register
     */
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> {
        val response = userService.register(request)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    /**
     * Login user
     * POST /users/login
     */
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        val response = userService.login(request)
        return ResponseEntity(response, HttpStatus.OK)
    }

    /**
     * Get user profile (requires authentication)
     * GET /users/profile
     */
    @GetMapping("/profile")
    fun getProfile(@RequestHeader(name = "Authorization") authHeader: String): ResponseEntity<ProfileResponse> {
        // Extract token from Authorization header
        val token = if (authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else {
            throw IllegalArgumentException("Invalid Authorization header format")
        }

        // Validate token and extract user ID
        if (!jwtTokenProvider.validateToken(token)) {
            throw IllegalArgumentException("Invalid or expired token")
        }

        val userId = jwtTokenProvider.getUserIdFromToken(token)
        val profile = userService.getProfile(userId)
        return ResponseEntity(profile, HttpStatus.OK)
    }

    /**
     * Get user profile by ID (requires authentication)
     * GET /users/{userId}
     */
    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<ProfileResponse> {
        val profile = userService.getProfile(userId)
        return ResponseEntity(profile, HttpStatus.OK)
    }
}

