package me.dio.teste.main.config.security

data class AuthenticationResponse(
    val role: String?,
    val accessToken: String,
    val refreshToken: String
)
