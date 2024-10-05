package br.com.devmeist3r.bancoapp.presenter.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.devmeist3r.bancoapp.data.model.User
import br.com.devmeist3r.bancoapp.domain.profile.SaveProfileUsecase
import br.com.devmeist3r.bancoapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val saveProfileUsecase: SaveProfileUsecase
) : ViewModel() {

    fun saveProfile(user: User) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            saveProfileUsecase.invoke(user)
            emit(StateView.Success(null))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }
}