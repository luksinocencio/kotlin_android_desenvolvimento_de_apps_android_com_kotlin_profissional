package br.com.devmeist3r.bancoapp.domain.wallet

import br.com.devmeist3r.bancoapp.data.model.Wallet
import br.com.devmeist3r.bancoapp.data.repository.wallet.WalletDataSourceImpl
import javax.inject.Inject

class InitWalletUsecase @Inject constructor(
    private val walletDataSourceImpl: WalletDataSourceImpl
) {
    suspend operator fun invoke(wallet: Wallet) {
        return walletDataSourceImpl.initWallet(wallet)
    }
}