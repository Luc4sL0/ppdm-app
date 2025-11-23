package com.example.ppd_p01.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.ppd_p01.domain.model.Habit
import com.example.ppd_p01.domain.model.HabitStatus
import com.example.ppd_p01.R

@Composable
fun HabitItem(
    habit: Habit,
    onStatusChange: (HabitStatus) -> Unit = {}
) {
    var showModal by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable{ showModal = true },
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (habit.status) {
                HabitStatus.COMPLETED -> colorResource(R.color.habit_completed)
                HabitStatus.PENDING -> colorResource(R.color.habit_pending)
                HabitStatus.UPCOMING -> colorResource(R.color.habit_upcoming)
            }
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(habit.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                "⏰ ${habit.time} • 🔁 ${habit.recurrence}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    if (showModal) {
        StatusModal(
            currentStatus = habit.status,
            onDismiss = { showModal = false },
            onSelect = {
                onStatusChange(it)
                showModal = false
            }
        )
    }
}
