package org.example.userservice.dto

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val id: Long,
    val name: String,
    val email: String,
    val token: String,
    val expiresIn: Long
)

data class ProfileResponse(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: String
)

