package me.dio.teste.main.service.impl

import me.dio.teste.main.domain.Admin
import me.dio.teste.main.repository.AdmRepository
import me.dio.teste.main.service.IAdmService
import org.springframework.stereotype.Service

@Service
class AdmService (
    private val adminRepository: AdmRepository
) : IAdmService {
    override fun save(admin: Admin): Admin = adminRepository.save(admin)

    override fun findById(id: Long): Admin = adminRepository.findById(id).orElseThrow{
        RuntimeException("Admin com id $id não encontrado")
    }
}