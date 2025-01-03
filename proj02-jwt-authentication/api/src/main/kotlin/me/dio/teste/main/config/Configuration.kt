package me.dio.teste.main.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.security.SecuritySchemes
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import me.dio.teste.main.config.security.CustomUserDetailService
import me.dio.teste.main.config.security.JwtAuthenticationFilter
import me.dio.teste.main.config.security.jwt.JwtProperties
import me.dio.teste.main.repository.UserAppRepository
import org.springframework.context.annotation.Configuration
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
@EnableWebSecurity
@SecuritySchemes(
    SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
)
class Configuration : OpenApiCustomizer {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authFilter: JwtAuthenticationFilter
    ) : DefaultSecurityFilterChain {
        http.csrf{it.disable()}.authorizeHttpRequests {
            auth ->
                auth.requestMatchers(
                    "/swagger-ui/**", "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/v3/api-docs/**"
                ).permitAll()
                auth.requestMatchers("/admin").hasRole("ADM")
                auth.requestMatchers("/chef").hasRole("CHEF")
                auth.requestMatchers( "/auth/**").permitAll()
                auth.anyRequest().authenticated()
        }.sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: CustomUserDetailService,
        passwordEncoder: PasswordEncoder) : AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)
        val providerManager = ProviderManager(authenticationProvider)
        providerManager.isEraseCredentialsAfterAuthentication = false
        return providerManager
    }

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(userAppRepository: UserAppRepository) : UserDetailsService =
        CustomUserDetailService(userAppRepository)

    override fun customise(openApi: OpenAPI?) {
        val info: Info = Info()
        info.apply {
            contact = Contact()
                .name("Adevan Neves Santos")
                .email("ans.eng20@uea.edu.br")
                .url("https://www.linkedin.com/in/adevancomp/")
            title = "Exemplo de API usando JWT"
            description = """
                API rest spring boot usando kotlin, jwt e granted authorities.
            """.trimIndent()
            version = "1.0.0"
        }
        openApi?.info = info
        openApi?.addSecurityItem(
            SecurityRequirement().addList("Bearer Authentication")
        )
    }
}