package me.dio.teste.main.controller

import me.dio.teste.main.dto.ChefRegister
import me.dio.teste.main.service.IChefService
import me.dio.teste.main.view.ChefView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chef")
class ChefResource (
    private val chefService: IChefService,
    private val encoder: PasswordEncoder
){
    @PostMapping
    fun save(dto: ChefRegister) : ResponseEntity<ChefView>{
        val chef = dto.toEntity()
        chef.password = encoder.encode(dto.password)
        val chefSaved = chefService.save(chef)
        return  ResponseEntity.status(HttpStatus.CREATED).body(chefSaved.toView())
    }
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) : ResponseEntity<ChefView>{
        val chef = chefService.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(chef.toView())
    }
}