package br.com.devmeist3r.bancoapp.presenter.auth.recover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.devmeist3r.bancoapp.domain.auth.RecoverUsecase
import br.com.devmeist3r.bancoapp.util.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RecoverViewModel @Inject constructor(
    private val recoverUsecase: RecoverUsecase
) : ViewModel() {
    fun recover(email: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            recoverUsecase.invoke(email)
            emit(StateView.Success(null))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }
}