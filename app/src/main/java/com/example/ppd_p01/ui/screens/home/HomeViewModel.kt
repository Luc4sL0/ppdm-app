package com.example.ppd_p01.ui.screens.home
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppd_p01.domain.model.Habit
import com.example.ppd_p01.domain.repository.HabitRepository
import kotlinx.coroutines.launch
class HomeViewModel(
    private val repository: HabitRepository,
    private val userId: Int
) : ViewModel() {

    var uiState by mutableStateOf<HabitState>(HabitState.Loading)
        private set

    init {
        loadHabits()
    }

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

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.addHabit(habit.copy(userId = userId))
            loadHabits()
        }
    }

    fun markHabitCompleted(habitId: Int) {
        viewModelScope.launch {
            repository.markCompleted(habitId)
            loadHabits()
        }
    }
}


sealed class HabitState {
    object Loading : HabitState()
    object Empty : HabitState()
    data class Success(val habits: List<Habit>) : HabitState()
    data class Error(val message: String) : HabitState()
}
