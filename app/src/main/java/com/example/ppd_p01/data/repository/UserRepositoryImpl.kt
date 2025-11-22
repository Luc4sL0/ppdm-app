package com.example.ppd_p01.data.repository

import com.example.ppd_p01.data.local.UserDao
import com.example.ppd_p01.data.local.UserEntity
import com.example.ppd_p01.domain.model.User
import com.example.ppd_p01.domain.repository.UserRepository

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {

    override suspend fun login(email: String, password: String): User? {
        return dao.login(email, password)?.let {
            User(it.id, it.email)
        }
    }

    override suspend fun register(email: String, password: String) {
        val alreadyExists = dao.findByEmail(email)

        if (alreadyExists != null) {
            throw Exception("Usuário já cadastrado")
        }

        dao.insert(
            UserEntity(
                email = email,
                password = password
            )
        )
    }

}
