package org.dqmdz.consumer.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBarPersona(navController: NavController, onBackToHome: () -> Unit) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = "Agregar") },
            label = { Text("Agregar") },
            selected = false,
            onClick = { navController.navigate("agregar") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Listar") },
            label = { Text("Listar") },
            selected = false,
            onClick = {
                // Volver a la pantalla de personas para listar
                navController.navigate("persona") {
                    popUpTo("persona") { inclusive = true }
                }
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") },
            label = { Text("Volver") },
            selected = false,
            onClick = { onBackToHome() }
        )
    }
}
