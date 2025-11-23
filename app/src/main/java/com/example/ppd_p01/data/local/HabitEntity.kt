package com.example.ppd_p01.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val time: String, // formato "HH:mm"
    val recurrence: String, // exemplo: "Seg, Ter, Qua"
    val status: String // "UPCOMING", "PENDING", "COMPLETED"
)
