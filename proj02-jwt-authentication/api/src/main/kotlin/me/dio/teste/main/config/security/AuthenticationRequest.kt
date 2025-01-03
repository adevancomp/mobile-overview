package me.dio.teste.main.config.security

data class AuthenticationRequest(
    val email: String,
    val password: String
)
