package com.example.ppd_p01.ui.screens.signup

import com.example.ppd_p01.domain.repository.UserRepository

class SignUpViewModelFactory(
    private val repository: UserRepository
) : androidx.lifecycle.ViewModelProvider.Factory {

    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}