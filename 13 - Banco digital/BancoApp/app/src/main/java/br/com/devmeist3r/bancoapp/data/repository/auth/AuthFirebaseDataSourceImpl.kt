package br.com.devmeist3r.bancoapp.data.repository.auth

import br.com.devmeist3r.bancoapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class AuthFirebaseDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthFirebaseDataSource {
    override suspend fun login(email: String, password: String) {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
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

    override suspend fun register(name: String, email: String, phone: String, password: String,): User {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result.user?.uid ?: ""
                        val user = User(
                            id = userId,
                            name = name,
                            email = email,
                            phone = phone
                        )
                        continuation.resumeWith(Result.success(user))
                    } else {
                        it.exception?.let { exceptionError ->
                            continuation.resumeWith(Result.failure(exceptionError))
                        }
                    }
                }
        }
    }

    override suspend fun recover(email: String) {
        return suspendCoroutine { continuation ->
            firebaseAuth.sendPasswordResetEmail(email)
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