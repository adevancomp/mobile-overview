package me.dio.teste.main.exception

data class DomainException(override val message: String) : RuntimeException(message)