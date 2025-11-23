package com.example.ppd_p01.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppd_p01.domain.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf<LoginState>(LoginState.Idle)
        private set

    fun login(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            uiState = LoginState.Error("Preencha email e senha")
            return
        }

        viewModelScope.launch {
            uiState = LoginState.Loading

            try {
                val user = repository.login(email, password)

                uiState = if (user != null) {
                    LoginState.Success
                } else {
                    LoginState.Error("Usuário não encontrado")
                }

            } catch (e: Exception) {
                uiState = LoginState.Error(
                    when {
                        e.message?.contains("password") == true ->
                            "Senha incorreta"
                        e.message?.contains("no user record") == true ->
                            "Usuário não existe"
                        e.message?.contains("email address is badly formatted") == true ->
                            "Email inválido"
                        else ->
                            "Erro ao fazer login"
                    }
                )
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
