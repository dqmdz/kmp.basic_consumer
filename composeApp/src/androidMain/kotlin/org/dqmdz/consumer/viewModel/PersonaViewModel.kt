package org.dqmdz.consumer.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.datetime.LocalDate
import model.Persona

class PersonaViewModel : ViewModel() {
    private val _personas = mutableStateListOf(
        Persona(1, "Daniel", "Soto", LocalDate(1990, 1, 1)),
        Persona(2, "María", "Pérez", LocalDate(1985, 6, 24))
    )
    val personas: List<Persona> get() = _personas

    fun addPersona(persona: Persona) {
        _personas.add(persona)
    }
}
