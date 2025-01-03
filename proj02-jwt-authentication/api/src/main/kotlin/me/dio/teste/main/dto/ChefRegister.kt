package me.dio.teste.main.dto

import me.dio.teste.main.domain.Chef
import java.math.BigDecimal

data class ChefRegister(
    val name: String,
    val email: String,
    val password: String,
    val salary: BigDecimal
) {
    fun toEntity() : Chef = Chef(
        name = this.name,
        email = this.email,
        password = this.password,
        salary = this.salary
    )
}
