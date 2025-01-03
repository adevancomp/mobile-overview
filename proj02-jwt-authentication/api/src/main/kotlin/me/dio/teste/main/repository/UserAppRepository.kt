package me.dio.teste.main.repository

import me.dio.teste.main.domain.UserApp
import org.springframework.data.jpa.repository.JpaRepository

interface UserAppRepository : JpaRepository<UserApp, Long> {
    fun findByEmail(email: String) : UserApp?
}