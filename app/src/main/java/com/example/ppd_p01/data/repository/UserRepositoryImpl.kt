package com.example.ppd_p01.data.repository

import com.example.ppd_p01.domain.model.User
import com.example.ppd_p01.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : UserRepository {

    override suspend fun login(email: String, password: String): User? {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        val firebaseUser = result.user ?: return null

        return User(
            id = firebaseUser.uid,
            email = firebaseUser.email ?: ""
        )
    }

    override suspend fun register(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            throw Exception(e.message ?: "Erro ao cadastrar usuário")
        }
    }
}
