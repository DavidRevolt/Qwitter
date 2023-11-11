package com.davidrevolt.qwitter.core.data.utils.authentication

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthenticationServiceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthenticationService {

    override val userLoggedIn: Boolean
        get() = firebaseAuth.currentUser != null

    override fun authState(): Flow<Boolean> = callbackFlow {
        val listener =
            FirebaseAuth.AuthStateListener { auth ->
                channel.trySend(auth.currentUser!=null)
            }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }


    override suspend fun authenticate(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun deleteAccount() {
        firebaseAuth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}