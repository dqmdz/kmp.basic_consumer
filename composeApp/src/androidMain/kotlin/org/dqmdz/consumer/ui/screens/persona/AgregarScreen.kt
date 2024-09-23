package org.dqmdz.consumer.ui.screens.persona

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dqmdz.consumer.viewModel.PersonaViewModel
import kotlinx.datetime.LocalDate
import model.Persona
import java.util.Calendar

@Composable
fun AgregarScreen(
    personaViewModel: PersonaViewModel, // Usamos el ViewModel aquí
    onPersonaAgregada: () -> Unit // Navegar después de agregar
) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var apellido by remember { mutableStateOf(TextFieldValue("")) }
    var fechaNacimiento by remember { mutableStateOf<LocalDate?>(null) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            androidx.compose.material.TopAppBar(
                title = { Text("Agregar Persona") }
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

                // Botón para seleccionar fecha de nacimiento
                Button(
                    onClick = {
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                fechaNacimiento = LocalDate(year, month + 1, dayOfMonth)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = fechaNacimiento?.toString() ?: "Seleccionar Fecha de Nacimiento")
                }

                // Botón para agregar la persona
                Button(
                    onClick = {
                        if (nombre.text.isNotEmpty() && apellido.text.isNotEmpty() && fechaNacimiento != null) {
                            val nuevaPersona = Persona(
                                id = null,
                                nombre = nombre.text,
                                apellido = apellido.text,
                                fechaNacimiento = fechaNacimiento!!
                            )
                            personaViewModel.addPersona(nuevaPersona) {
                                onPersonaAgregada() // Volver a la pantalla de listar
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar", fontSize = 18.sp)
                }
            }
        }
    )
}
