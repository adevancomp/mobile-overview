package me.dio.teste.main.service.impl

import me.dio.teste.main.domain.Chef
import me.dio.teste.main.repository.ChefRepository
import me.dio.teste.main.service.IChefService
import org.springframework.stereotype.Service

@Service
class ChefService(
    private val chefRepository: ChefRepository
) : IChefService {
    override fun save(chef: Chef): Chef = chefRepository.save(chef)

    override fun findById(id: Long): Chef = chefRepository.findById(id).orElseThrow{
        RuntimeException("Chef com id $id não encontrado")
    }
}