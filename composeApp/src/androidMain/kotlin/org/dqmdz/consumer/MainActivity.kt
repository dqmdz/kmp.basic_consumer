package org.dqmdz.consumer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.dqmdz.consumer.ui.BottomBar
import org.dqmdz.consumer.ui.BottomBarPersona
import org.dqmdz.consumer.ui.screens.AboutScreen
import org.dqmdz.consumer.ui.screens.HomeScreen
import org.dqmdz.consumer.ui.screens.persona.AgregarScreen
import org.dqmdz.consumer.ui.screens.persona.ListarScreen
import org.dqmdz.consumer.viewModel.PersonaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val personaViewModel = PersonaViewModel()
            MainScreen(personaViewModel)
        }
    }
}

@Composable
fun MainScreen(personaViewModel: PersonaViewModel) {
    val navController = rememberNavController()
    var showPersonaBottomBar by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (showPersonaBottomBar) {
                BottomBarPersona(
                    navController = navController,
                    onBackToHome = {
                        showPersonaBottomBar = false
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = false }
                        }
                    }
                )
            } else {
                BottomBar(navController)
            }
        }
    ) { contentPadding: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(contentPadding) // Asegurarnos de usar el padding
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("persona") {
                showPersonaBottomBar = true
                ListarScreen() // Directamente muestra la pantalla de Listar al seleccionar Persona
            }
            composable("about") {
                AboutScreen()
            }
            composable("agregar") {
                AgregarScreen()
            }
            composable("listar") {
                ListarScreen()
            }
        }
    }
}