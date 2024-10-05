package br.com.devmeist3r.bancoapp.presenter.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.devmeist3r.bancoapp.data.model.Wallet
import br.com.devmeist3r.bancoapp.domain.wallet.InitWalletUsecase
import br.com.devmeist3r.bancoapp.util.StateView
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class WalletViewModel @Inject constructor(
    private val initWallerUsecase: InitWalletUsecase
) : ViewModel() {

    fun initWallet(wallet: Wallet) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            initWallerUsecase.invoke(wallet)
            emit(StateView.Success(null))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }
    }
}