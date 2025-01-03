package me.dio.teste.main.repository

import me.dio.teste.main.domain.Admin
import org.springframework.data.jpa.repository.JpaRepository

interface AdmRepository : JpaRepository<Admin,Long> {
}