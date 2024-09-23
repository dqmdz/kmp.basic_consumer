package org.dqmdz.consumer.ui.screens.persona

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Persona

@Composable
fun PersonaCard(
    persona: Persona,
    onDelete: (Persona) -> Unit,
    onEdit: (Int) -> Unit // Nueva función para manejar la edición
) {
    var showDialog by remember { mutableStateOf(false) }

    // Mostrar el diálogo de confirmación
    if (showDialog) {
        EliminarPersonaDialog(
            personaNombre = "${persona.nombre} ${persona.apellido}",
            onConfirm = {
                showDialog = false
                onDelete(persona)
            },
            onDismiss = {
                showDialog = false
            }
        )
    }

    // Card de persona
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "ID: ${persona.id}", fontSize = 14.sp, color = Color.Gray)
            Text(text = "${persona.nombre} ${persona.apellido}", fontSize = 18.sp)
            Text(text = "Fecha de Nacimiento: ${persona.fechaNacimiento}", fontSize = 14.sp, color = Color.Gray)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
                IconButton(onClick = { onEdit(persona.id!!) }) { // Navegar a la pantalla de edición
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Editar", tint = Color.Blue)
                }
            }
        }
    }
}
