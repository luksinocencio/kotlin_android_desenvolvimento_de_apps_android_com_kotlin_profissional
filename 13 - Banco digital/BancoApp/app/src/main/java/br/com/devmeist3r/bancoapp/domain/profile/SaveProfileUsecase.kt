package br.com.devmeist3r.bancoapp.domain.profile

import br.com.devmeist3r.bancoapp.data.model.User
import br.com.devmeist3r.bancoapp.data.repository.profile.ProfileDataSourceImpl
import javax.inject.Inject

class SaveProfileUsecase @Inject constructor(
    private val profileRepositoryImpl: ProfileDataSourceImpl
) {
    suspend operator fun invoke(user: User) {
        return profileRepositoryImpl.saveProfile(user)
    }
}