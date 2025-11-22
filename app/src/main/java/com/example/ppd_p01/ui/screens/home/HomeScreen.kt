package com.example.ppd_p01.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Home") }
            )
        }
    ) { paddingValues ->

        Text(
            text = "Bem-vindo à Home!",
            modifier = Modifier.padding(paddingValues)
        )
    }
}
