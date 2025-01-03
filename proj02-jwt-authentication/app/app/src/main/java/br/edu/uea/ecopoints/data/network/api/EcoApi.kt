package br.edu.uea.ecopoints.data.network.api

import br.edu.uea.ecopoints.data.network.requests.AuthenticationRequest
import br.edu.uea.ecopoints.data.network.responses.AuthenticationResponse
import br.edu.uea.ecopoints.domain.Adm
import br.edu.uea.ecopoints.domain.Chef
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EcoApi {
    @POST("/auth")
    suspend fun login(@Body user: AuthenticationRequest) : Response<AuthenticationResponse>

    @GET("/chef/{id}")
    suspend fun getChefById(@Path("id") id: Long) : Response<Chef>

    @GET("/admin/{id}")
    suspend fun getAdminById(@Path("id") id: Long) : Response<Adm>
}