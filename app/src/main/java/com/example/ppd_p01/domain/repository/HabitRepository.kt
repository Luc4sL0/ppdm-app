package com.example.ppd_p01.domain.repository

import com.example.ppd_p01.domain.model.Habit

interface HabitRepository {
    suspend fun getHabits(userId: Int): List<Habit>
    suspend fun addHabit(habit: Habit)
    suspend fun markCompleted(habitId: Int)
}
