package me.dio.teste.main.dto

import me.dio.teste.main.domain.Admin

data class AdmRegister(
    val name: String,
    val email: String,
    val password: String,
    val secretKey: String
) {
    fun toEntity() : Admin = Admin(
        name = this.name,
        email = this.email,
        password = this.password,
        secretKey = this.secretKey
    )
}
