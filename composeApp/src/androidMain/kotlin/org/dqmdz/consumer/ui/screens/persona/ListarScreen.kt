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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.dqmdz.consumer.viewModel.PersonaViewModel

@Composable
fun ListarScreen(
    personaViewModel: PersonaViewModel,
    onNavigateToAgregar: () -> Unit,
    onNavigateToModificar: (Int) -> Unit // Navegación a la pantalla de modificación
) {
    // Cargar los datos cuando la pantalla se inicializa
    LaunchedEffect(Unit) {
        personaViewModel.loadPersonas()
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
                items(personaViewModel.personas) { persona ->
                    PersonaCard(
                        persona = persona,
                        onDelete = {
                            personaViewModel.deletePersona(persona.id) {
                                personaViewModel.loadPersonas()
                            }
                        },
                        onEdit = { personaId ->
                            onNavigateToModificar(personaId) // Navegar a modificar
                        }
                    )
                }
            }
        }
    )
}
