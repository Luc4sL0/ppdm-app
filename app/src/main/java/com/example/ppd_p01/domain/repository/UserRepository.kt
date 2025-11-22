package com.example.ppd_p01.domain.repository

import com.example.ppd_p01.domain.model.User

interface UserRepository {
    suspend fun login(email: String, password: String): User?
    suspend fun register(email: String, password: String)
}
