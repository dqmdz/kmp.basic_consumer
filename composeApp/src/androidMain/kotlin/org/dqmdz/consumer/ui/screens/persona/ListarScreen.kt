package org.dqmdz.consumer.ui.screens.persona

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import client.deletePersona
import client.getPersonaData
import kotlinx.coroutines.launch
import model.Persona

@Composable
fun ListarScreen(
    onNavigateToAgregar: () -> Unit // Parámetro de navegación para agregar, pero sin botón extra
) {
    var personas by remember { mutableStateOf<List<Persona>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    // Cargar los datos desde la API
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val apiResponse = getPersonaData()
            personas = apiResponse
        }
    }

    // Función para eliminar una persona y refrescar la lista
    fun eliminarPersona(persona: Persona) {
        coroutineScope.launch {
            deletePersona(persona.id) // Eliminar la persona usando el backend
            // Refrescar la lista de personas después de la eliminación
            personas = getPersonaData()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Personas",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(personas) { persona ->
                    PersonaCard(persona, onDelete = { eliminarPersona(it) })
                }
            }
        }
    )
}