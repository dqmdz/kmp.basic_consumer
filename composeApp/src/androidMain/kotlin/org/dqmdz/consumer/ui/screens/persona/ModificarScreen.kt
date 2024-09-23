package org.dqmdz.consumer.ui.screens.persona

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import client.updatePersona
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import model.Persona
import java.util.Calendar

@Composable
fun ModificarScreen(
    persona: Persona,
    onPersonaModificada: () -> Unit // Llamar esta función para volver a la lista
) {
    var nombre by remember { mutableStateOf(persona.nombre) }
    var apellido by remember { mutableStateOf(persona.apellido) }
    var fechaNacimiento by remember { mutableStateOf(persona.fechaNacimiento) }
    val coroutineScope = rememberCoroutineScope()

    // Contexto necesario para mostrar el DatePicker
    val context = LocalContext.current

    // Variables para manejar el estado del DatePicker
    val calendar = Calendar.getInstance()

    // Obtener los valores actuales de la fecha para inicializar el DatePicker
    val initialYear = fechaNacimiento.year
    val initialMonth = fechaNacimiento.monthNumber - 1 // Los meses en Calendar comienzan desde 0
    val initialDay = fechaNacimiento.dayOfMonth

    // Mostrar el DatePickerDialog para seleccionar una fecha
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            fechaNacimiento = LocalDate(year, month + 1, dayOfMonth) // Actualizamos la fecha con lo seleccionado
        },
        initialYear, initialMonth, initialDay
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Modificar Persona") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Campo de texto para nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Campo de texto para apellido
                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Botón para mostrar el DatePicker y seleccionar la fecha de nacimiento
                Button(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = fechaNacimiento.toString() ?: "Seleccionar Fecha de Nacimiento")
                }

                // Botón para guardar los cambios
                Button(
                    onClick = {
                        if (nombre.isNotEmpty() && apellido.isNotEmpty()) {
                            coroutineScope.launch {
                                // Llamar a updatePersona con el ID y los nuevos datos
                                updatePersona(
                                    personaId = persona.id,
                                    persona = persona.copy(
                                        nombre = nombre,
                                        apellido = apellido,
                                        fechaNacimiento = fechaNacimiento
                                    )
                                )
                                onPersonaModificada() // Volver a la pantalla de listar
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar", fontSize = 18.sp)
                }
            }
        }
    )
}