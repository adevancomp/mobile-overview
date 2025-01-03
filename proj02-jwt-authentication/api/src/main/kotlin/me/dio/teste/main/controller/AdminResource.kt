package me.dio.teste.main.controller

import me.dio.teste.main.dto.AdmRegister
import me.dio.teste.main.service.IAdmService
import me.dio.teste.main.view.AdmView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminResource (
    private val adminService: IAdmService,
    private val encoder: PasswordEncoder
){
    @PostMapping
    fun save(dto: AdmRegister) : ResponseEntity<AdmView>{
        val admin = dto.toEntity()
        admin.password = encoder.encode(dto.password)
        val adminSaved = adminService.save(admin)
        return ResponseEntity.status(HttpStatus.OK).body(adminSaved.toView())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) : ResponseEntity<AdmView>{
        val admin = adminService.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(admin.toView())
    }
}