package com.davidrevolt.qwitter.core.data.repository

import com.davidrevolt.qwitter.core.model.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() :UserRepository{
    private val authService: FirebaseAuth = FirebaseAuth.getInstance()

    // Methods with await throws error if task fail


    override val userLoggedIn: Boolean
        get() =  authService.currentUser != null

    // callbackFlow is a flow builder that lets you convert callback-based APIs into flows
    // can used for getting authentication state, responds to changes in the user's sign-in state at runtime
    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let {
                        User(
                            it.uid,
                            if (it.displayName != null) it.displayName!! else "",
                            it.isAnonymous
                        )
                    } ?: User())
                }
            authService.addAuthStateListener(listener)
            awaitClose { authService.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) {
        authService.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        authService.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        authService.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        authService.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun deleteAccount() {
        authService.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        authService.signOut()
    }
}