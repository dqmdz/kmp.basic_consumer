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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.dqmdz.consumer.viewModel.PersonaViewModel
import java.util.Calendar

@Composable
fun ModificarScreen(
    personaId: Int,
    personaViewModel: PersonaViewModel,
    onPersonaModificada: () -> Unit
) {
    val persona = personaViewModel.getPersonaById(personaId)

    var nombre by remember { mutableStateOf(persona?.nombre ?: "") }
    var apellido by remember { mutableStateOf(persona?.apellido ?: "") }
    var fechaNacimiento by remember { mutableStateOf(persona?.fechaNacimiento ?: LocalDate(2000, 1, 1)) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            fechaNacimiento = LocalDate(year, month + 1, dayOfMonth)
        },
        fechaNacimiento.year, fechaNacimiento.monthNumber - 1, fechaNacimiento.dayOfMonth
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
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = fechaNacimiento.toString() ?: "Seleccionar Fecha de Nacimiento")
                }

                Button(
                    onClick = {
                        if (nombre.isNotEmpty() && apellido.isNotEmpty()) {
                            coroutineScope.launch {
                                personaViewModel.updatePersona(
                                    persona = persona!!.copy(
                                        nombre = nombre,
                                        apellido = apellido,
                                        fechaNacimiento = fechaNacimiento
                                    ),
                                    onSuccess = {
                                        onPersonaModificada()
                                    }
                                )
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
