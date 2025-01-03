package br.edu.uea.ecopoints.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Adm(
    @JsonProperty("id") val id: Long,
    @JsonProperty("email") val email: String,
    @JsonProperty("secretKey") val secretKey: String
)
