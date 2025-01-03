package br.edu.uea.ecopoints.data.network.requests

data class AuthenticationRequest(
    val email: String,
    val password: String
)
