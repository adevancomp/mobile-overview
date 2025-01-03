package me.dio.teste.main.repository

import me.dio.teste.main.domain.Chef
import org.springframework.data.jpa.repository.JpaRepository

interface ChefRepository : JpaRepository<Chef,Long> {
}