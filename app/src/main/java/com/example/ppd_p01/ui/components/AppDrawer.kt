package com.example.ppd_p01.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    onNavigate: (String) -> Unit,
    onCloseDrawer: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Menu", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))
        TextButton(onClick = { onNavigate("home"); onCloseDrawer() }) {
            Text("🏠 Início")
        }
        TextButton(onClick = { onNavigate("profile"); onCloseDrawer() }) {
            Text("👤 Perfil")
        }
        TextButton(onClick = { onNavigate("settings"); onCloseDrawer() }) {
            Text("⚙️ Configurações")
        }
    }
}
