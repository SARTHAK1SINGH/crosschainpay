package org.example.userservice.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class RequestUtil {

    fun getUserIdFromRequest(request: HttpServletRequest): Long {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw IllegalArgumentException("Authorization header is missing or invalid")
        }

        // Token extraction logic will be handled by JwtTokenProvider
        // This is just a placeholder for future enhancements
        return 0L
    }

    fun getTokenFromRequest(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.substring(7)
        } else {
            null
        }
    }
}

