package org.dqmdz.consumer.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen() {
    val androidVersion = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
    val kotlinVersion = "Kotlin 1.9.10" // Puedes ajustar esta versión
    val kmpVersion = "KMP v0.4.0"  // Si es relevante, puedes ajustarla o mantenerla estática

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About This App",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Card for Android version
            InfoCard(title = "Android Version", info = androidVersion)

            Spacer(modifier = Modifier.height(16.dp))

            // Card for Kotlin version
            InfoCard(title = "Kotlin Version", info = kotlinVersion)

            Spacer(modifier = Modifier.height(16.dp))

            // Card for KMP version
            InfoCard(title = "KMP Version", info = kmpVersion)
        }
    }
}

@Composable
fun InfoCard(title: String, info: String) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = info,
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp
            )
        }
    }
}