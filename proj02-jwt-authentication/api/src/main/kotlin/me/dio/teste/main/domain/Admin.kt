package me.dio.teste.main.domain

import jakarta.persistence.Entity
import me.dio.teste.main.enums.UserRole
import me.dio.teste.main.view.AdmView
import org.springframework.security.core.userdetails.User

@Entity
open class Admin(
    name: String,
    email: String,
    password: String,
    val secretKey: String
) : UserApp(null,name,email,password, UserRole.ROLE_ADM) {

    protected constructor() : this("", "", "", "")

    fun toView() : AdmView = AdmView(
        id = this.id!!,
        email = this.email,
        secretKey = this.secretKey
    )
}
