package br.com.devmeist3r.bancoapp.data.repository.wallet

import br.com.devmeist3r.bancoapp.data.model.Wallet
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class WalletDataSourceImpl @Inject constructor(
    database: FirebaseDatabase
) : WalletDataSource {
    private val walletPreference = database.reference
        .child("wallet")

    override suspend fun initWallet(wallet: Wallet) {
        return suspendCoroutine { continuation ->
            walletPreference
                .child(wallet.id)
                .setValue(wallet)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    } else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }
}