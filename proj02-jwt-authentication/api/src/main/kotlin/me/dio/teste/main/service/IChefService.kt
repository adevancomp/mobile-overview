package me.dio.teste.main.service

import me.dio.teste.main.domain.Chef

interface IChefService {
    fun save(chef: Chef) : Chef
    fun findById(id: Long) : Chef
}