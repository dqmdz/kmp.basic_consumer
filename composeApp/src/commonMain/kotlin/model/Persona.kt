package model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import serializer.LocalDateSerializer

@Serializable
data class Persona(
    val id: Int? = null, // El ID se asignará por el backend
    val nombre: String,
    val apellido: String,
    @Serializable(with = LocalDateSerializer::class) // Aplicamos el serializador a LocalDate
    val fechaNacimiento: LocalDate
)