package com.example.ppd_p01.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppd_p01.domain.repository.UserRepository
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf<RegisterState>(RegisterState.Idle)
        private set

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                uiState = RegisterState.Loading
                repository.register(email, password)
                uiState = RegisterState.Success
            } catch (e: Exception) {
                uiState = RegisterState.Error(
                    e.message ?: "Erro ao cadastrar"
                )
            }
        }
    }
}

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
