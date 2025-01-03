package me.dio.teste.main.domain

import jakarta.persistence.*
import me.dio.teste.main.enums.UserRole

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class UserApp(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,
    open val name: String,
    open val email: String,
    open var password: String,
    @Enumerated(EnumType.STRING)
    open val role: UserRole
) {
    protected constructor() : this(null, "", "", "", UserRole.ROLE_USER)
}
