package br.com.devmeist3r.bancoapp.domain.auth

import br.com.devmeist3r.bancoapp.data.model.User
import br.com.devmeist3r.bancoapp.data.repository.auth.AuthFirebaseDataSourceImpl
import javax.inject.Inject

class RegisterUsecase @Inject constructor(
    private val authFirabaseDataSourceImpl: AuthFirebaseDataSourceImpl
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        phone: String,
        password: String
    ): User {
        return authFirabaseDataSourceImpl.register(name, email, phone, password)
    }
}