package org.dqmdz.consumer.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import client.addPersona
import client.deletePersona
import client.getPersonaData
import client.updatePersona
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Persona

class PersonaViewModel : ViewModel() {
    private val _personas = mutableStateListOf<Persona>()
    val personas: List<Persona> get() = _personas

    // Función para cargar las personas desde la API
    fun loadPersonas() {
        viewModelScope.launch {
            val apiResponse = getPersonaData()
            _personas.clear()
            _personas.addAll(apiResponse)
        }
    }

    // Función para obtener una persona por ID
    fun getPersonaById(id: Int?): Persona? {
        return _personas.find { it.id == id }
    }

    // Función para agregar una persona al backend y actualizar la lista
    fun addPersona(persona: Persona, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val addedPersona = addPersona(persona)
            _personas.add(addedPersona) // Añadir la persona agregada a la lista local
            onSuccess() // Indicar que la operación fue exitosa
        }
    }

    // Función para eliminar una persona del backend y actualizar la lista
    fun deletePersona(personaId: Int?, onSuccess: () -> Unit) {
        viewModelScope.launch {
            deletePersona(personaId)
            _personas.removeIf { it.id == personaId } // Eliminar la persona de la lista local
            onSuccess() // Indicar que la operación fue exitosa
        }
    }

    // Función para actualizar una persona en el backend y actualizar la lista local
    fun updatePersona(persona: Persona, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val updatedPersona = updatePersona(persona.id, persona)
            _personas[_personas.indexOfFirst { it.id == persona.id }] = updatedPersona // Actualizar la persona en la lista local
            onSuccess() // Indicar que la operación fue exitosa
        }
    }
}
