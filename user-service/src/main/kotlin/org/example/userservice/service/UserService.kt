package org.example.userservice.service

import org.example.userservice.dto.AuthResponse
import org.example.userservice.dto.LoginRequest
import org.example.userservice.dto.ProfileResponse
import org.example.userservice.dto.RegisterRequest
import org.example.userservice.exception.InvalidCredentialsException
import org.example.userservice.exception.UserAlreadyExistsException
import org.example.userservice.exception.UserNotFoundException
import org.example.userservice.model.User
import org.example.userservice.repository.UserRepository
import org.example.userservice.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    fun register(request: RegisterRequest): AuthResponse {
        // Check if user already exists
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException("User with email ${request.email} already exists")
        }

        // Create and save new user
        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )

        val savedUser = userRepository.save(user)

        // Generate JWT token
        val token = jwtTokenProvider.generateToken(savedUser.email, savedUser.id)

        return AuthResponse(
            id = savedUser.id,
            name = savedUser.name,
            email = savedUser.email,
            token = token,
            expiresIn = jwtTokenProvider.getExpirationTime()
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        // Find user by email
        val user = userRepository.findByEmail(request.email)
            .orElseThrow { UserNotFoundException("User not found with email: ${request.email}") }

        // Verify password
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialsException("Invalid email or password")
        }

        // Generate JWT token
        val token = jwtTokenProvider.generateToken(user.email, user.id)

        return AuthResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            token = token,
            expiresIn = jwtTokenProvider.getExpirationTime()
        )
    }

    fun getProfile(userId: Long): ProfileResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException("User not found with id: $userId") }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        return ProfileResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            createdAt = user.createdAt.format(formatter)
        )
    }

    fun getUserById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { UserNotFoundException("User not found with id: $userId") }
    }
}

