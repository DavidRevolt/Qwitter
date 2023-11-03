package com.davidrevolt.qwitter.core.data.utils.authentication

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthenticationServiceImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) : AuthenticationService {

    override val userLoggedIn: Boolean
        get() =  firebaseAuth.currentUser != null


    // Methods with await throws error if task fail
    override suspend fun authenticate(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
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