package br.com.devmeist3r.bancoapp.presenter.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.devmeist3r.bancoapp.domain.auth.RegisterUsecase
import br.com.devmeist3r.bancoapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUsecase: RegisterUsecase
) : ViewModel() {
    fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            val user = registerUsecase.invoke(
                name,
                email,
                phone,
                password,
            )
            emit(StateView.Success(user))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }
}