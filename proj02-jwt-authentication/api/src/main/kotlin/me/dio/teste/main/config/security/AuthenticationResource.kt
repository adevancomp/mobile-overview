package me.dio.teste.main.config.security

import io.jsonwebtoken.ExpiredJwtException
import me.dio.teste.main.dto.AdmRegister
import me.dio.teste.main.dto.ChefRegister
import me.dio.teste.main.service.IAdmService
import me.dio.teste.main.service.IChefService
import me.dio.teste.main.view.AdmView
import me.dio.teste.main.view.ChefView
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
class AuthenticationResource (
    private val authService: AuthenticationService,
    private val chefService: IChefService,
    private val adminService: IAdmService,
    private val encoder: PasswordEncoder
) {
    @PostMapping
    fun authenticate(
        @RequestBody authRequest: AuthenticationRequest
    ) : ResponseEntity<AuthenticationResponse> {
        val authenticationResponse = authService.authentication(authRequest)
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponse)
    }

    @PostMapping("/chef")
    fun saveChef(chefDto: ChefRegister) : ResponseEntity<ChefView>{
        val chef = chefDto.toEntity()
        chef.password = encoder.encode(chefDto.password)
        val chefSaved = chefService.save(chef)
        return  ResponseEntity.status(HttpStatus.CREATED).body(chefSaved.toView())
    }

    @PostMapping("/admin")
    fun saveAdmin(adminDto: AdmRegister) : ResponseEntity<AdmView>{
        val admin = adminDto.toEntity()
        admin.password = encoder.encode(adminDto.password)
        val adminSaved = adminService.save(admin)
        return ResponseEntity.status(HttpStatus.OK).body(adminSaved.toView())
    }

    @PostMapping("/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshTokenRequest) : ResponseEntity<TokenResponse>{
        var tokenResponse : TokenResponse? = null
        try {
            tokenResponse = authService.refreshAccessToken(refreshToken = request.token)?.mapToTokenResponse()
        } catch (ex: ExpiredJwtException){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(TokenResponse(""))
        }
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse)
    }

    private fun String.mapToTokenResponse(): TokenResponse = TokenResponse(
        token = this
    )
}