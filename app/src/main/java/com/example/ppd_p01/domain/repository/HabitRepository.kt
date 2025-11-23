package com.example.ppd_p01.domain.repository

import com.example.ppd_p01.domain.model.Habit
import com.example.ppd_p01.domain.model.HabitStatus

interface HabitRepository {
    suspend fun getHabits(userId: Int): List<Habit>
    suspend fun addHabit(habit: Habit)
    suspend fun updateStatus(habitId: Int, newStatus: HabitStatus)
}
