package com.example.ppd_p01.domain.model

data class Habit(
    val id: Int,
    val userId: Int,
    val title: String,
    val time: String,
    val recurrence: String,
    val status: HabitStatus
)

enum class HabitStatus {
    UPCOMING, PENDING, COMPLETED
}
