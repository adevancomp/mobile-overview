package me.dio.teste.main.domain

import jakarta.persistence.Entity
import me.dio.teste.main.enums.UserRole
import me.dio.teste.main.view.ChefView
import java.math.BigDecimal

@Entity
open class Chef(
    name: String,
    email: String,
    password: String,
    val salary: BigDecimal
) : UserApp(null,name,email,password, UserRole.ROLE_CHEF){

    protected constructor() : this("", "", "", BigDecimal.ZERO)

    fun toView() : ChefView = ChefView(
        id = this.id!!,
        name = this.name,
        email = this.email,
        salary = this.salary
    )
}
