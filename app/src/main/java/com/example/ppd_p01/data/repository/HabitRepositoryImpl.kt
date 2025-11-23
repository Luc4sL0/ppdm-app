package com.example.ppd_p01.data.repository

import com.example.ppd_p01.data.local.HabitDao
import com.example.ppd_p01.data.local.HabitEntity
import com.example.ppd_p01.domain.model.Habit
import com.example.ppd_p01.domain.model.HabitStatus
import com.example.ppd_p01.domain.repository.HabitRepository

class HabitRepositoryImpl(private val dao: HabitDao) : HabitRepository {

    override suspend fun getHabits(): List<Habit> {
        return dao.getAll().map {
            Habit(
                id = it.id,
                title = it.title,
                time = it.time,
                recurrence = it.recurrence,
                status = HabitStatus.valueOf(it.status)
            )
        }
    }

    override suspend fun addHabit(habit: Habit) {
        dao.insert(
            HabitEntity(
                title = habit.title,
                time = habit.time,
                recurrence = habit.recurrence,
                status = habit.status.name
            )
        )
    }

    override suspend fun markCompleted(habitId: Int) {
        dao.updateStatus(habitId, HabitStatus.COMPLETED.name)
    }
}
