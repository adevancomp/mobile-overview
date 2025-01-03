package me.dio.teste.main.config.security

import jakarta.transaction.Transactional
import me.dio.teste.main.repository.UserAppRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service
class CustomUserDetailService(
    private val userAppRepository: UserAppRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        println(username)
        val user = userAppRepository.findByEmail(username) ?: throw UsernameNotFoundException("Usuário com name $username não encontrado")
        println("${user.email} ${user.role}")
        return User(user.email, user.password, true, true, true, true, mutableListOf(SimpleGrantedAuthority(user.role.toString())))
    }
}