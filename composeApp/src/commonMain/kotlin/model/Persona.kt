package model

import kotlinx.datetime.LocalDate

data class Persona(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: LocalDate
)