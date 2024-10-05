package br.com.devmeist3r.bancoapp.data.repository.profile

import br.com.devmeist3r.bancoapp.data.model.User

interface ProfileDataSource {
    suspend fun saveProfile(user: User)
}