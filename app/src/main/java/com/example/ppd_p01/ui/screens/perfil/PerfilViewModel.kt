package com.example.ppd_p01.ui.screens.perfil

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppd_p01.domain.model.HabitStatus
import com.example.ppd_p01.domain.repository.HabitRepository
import com.example.ppd_p01.ui.screens.home.HabitState
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repository: HabitRepository,
    private val userId: Int
) : ViewModel() {

    var uiState by mutableStateOf<HabitState>(HabitState.Loading)
        private set

    init {
        loadHabits()
    }

    val totalPoints: Int
        get() = when (val state = uiState) {
            is HabitState.Success -> state.habits.sumOf { habit ->
                when (habit.status) {
                    HabitStatus.COMPLETED -> 10
                    HabitStatus.PENDING -> 5
                    HabitStatus.UPCOMING -> 2
                } as Int
            }
            else -> 0
        }

    val level: Int
        get() = totalPoints / 100

    val stars: Int
        get() = level.coerceAtMost(5)


    fun loadHabits() {
        viewModelScope.launch {
            try {
                val habits = repository.getHabits(userId)
                uiState = if (habits.isNotEmpty()) {
                    HabitState.Success(habits)
                } else {
                    HabitState.Empty
                }
            } catch (e: Exception) {
                uiState = HabitState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }


}