package com.example.ppd_p01.domain.repository

import com.example.ppd_p01.domain.model.Habit
import com.example.ppd_p01.domain.model.User

interface HabitRepository {
    suspend fun getHabits(): List<Habit>
    suspend fun addHabit(habit: Habit)
    suspend fun markCompleted(habitId: Int)
}