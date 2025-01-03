package br.edu.uea.ecopoints.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class Chef(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("salary") val salary: BigDecimal
)
