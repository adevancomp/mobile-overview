package me.dio.teste.main.service

import me.dio.teste.main.domain.Admin

interface IAdmService {
    fun save(admin: Admin) : Admin
    fun findById(id: Long) : Admin
}