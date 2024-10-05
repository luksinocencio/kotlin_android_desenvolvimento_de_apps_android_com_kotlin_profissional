package br.com.devmeist3r.bancoapp.data.repository.auth

import br.com.devmeist3r.bancoapp.data.model.User

interface AuthFirebaseDataSource {
    suspend fun login(
        email: String,
        password: String
    )

    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ): User

    suspend fun recover(
        email: String
    )
}