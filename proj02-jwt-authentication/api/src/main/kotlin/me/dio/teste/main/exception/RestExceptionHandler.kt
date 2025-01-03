package me.dio.teste.main.exception

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(DomainException::class)
    fun handleExpiredJwtException(
        exception: DomainException
    ) : ResponseEntity<String>{
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expirado")
    }
}