package com.turbohesap.account

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String
)

data class JwtResponse(
    val token: String,
    val id: Long,
    val username: String,
    val roles: List<String>
) {
    val type = "Bearer"
}