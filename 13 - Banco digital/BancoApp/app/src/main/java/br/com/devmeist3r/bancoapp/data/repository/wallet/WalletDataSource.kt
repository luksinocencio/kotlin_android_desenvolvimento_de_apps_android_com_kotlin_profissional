package br.com.devmeist3r.bancoapp.data.repository.wallet

import br.com.devmeist3r.bancoapp.data.model.Wallet

interface WalletDataSource {
    suspend fun initWallet(wallet: Wallet)
}