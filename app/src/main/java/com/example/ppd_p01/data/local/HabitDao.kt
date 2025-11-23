package com.example.ppd_p01.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits WHERE userId = :userId")
    suspend fun getAllByUser(userId: Int): List<HabitEntity>

    @Insert
    suspend fun insert(habit: HabitEntity)


    @Query("UPDATE habits SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)
}
