package com.example.ppd_p01.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ppd_p01.domain.model.Habit
import com.example.ppd_p01.domain.model.HabitStatus

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onConfirm: (Habit) -> Unit,
    userId: Int
) {
    var title by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var recurrence by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(HabitStatus.UPCOMING) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (title.isNotBlank() && time.isNotBlank()) {
                    onConfirm(
                        Habit(
                            id = 0,
                            title = title,
                            time = time,
                            recurrence = recurrence,
                            userId = userId,
                            status = status
                        )
                    )
                    onDismiss()
                }
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Novo Hábito") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Horário (ex: 08:30)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = recurrence,
                    onValueChange = { recurrence = it },
                    label = { Text("Recorrência (ex: Seg, Ter)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                DropdownMenuStatusSelector(
                    selected = status,
                    onSelect = { status = it }
                )
            }
        }
    )
}
