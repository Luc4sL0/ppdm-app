package com.example.ppd_p01.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ppd_p01.domain.model.HabitStatus

@Composable
fun StatusModal(
    currentStatus: HabitStatus,
    onDismiss: () -> Unit,
    onSelect: (HabitStatus) -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Alterar status do hábito")
        },
        text = {
            Column {
                HabitStatus.values().forEach { status ->
                    Text(
                        text = when (status) {
                            HabitStatus.UPCOMING -> "Em breve"
                            HabitStatus.PENDING -> "Pendente"
                            HabitStatus.COMPLETED -> "Concluído"
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clickable { onSelect(status) },
                        style = if (status == currentStatus)
                            MaterialTheme.typography.titleMedium
                        else
                            MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}
