package com.davidrevolt.qwitter.core.auth

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/*
* we don’t have to use the addOnCompleteListener in the API calls anymore,
* because we no longer need to pass the result to a callback.
* What we do instead, is use the await() method. Once you call await(),
* you suspend the function call, thus preventing the thread from being blocked.
* Note that you can only call
*
* In viewmodel we are executing these calls inside a launchCatching block:
* if an error happens on the first line, the exception will be automatically caught and handled,
* and the second line will not be reached at all.
* */


class AuthenticationServiceImpl @Inject constructor() :
    AuthenticationService {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override val currentUserId: String?
        get() = auth.currentUser?.uid.orEmpty()

    override val userLoggedIn: Boolean
        get() = auth.currentUser != null

    // TODO: choose what to use for state handling
    val user = auth.currentUser?.let {
        User(
            it.uid,
            if (it.displayName != null) it.displayName!! else "",
            it.isAnonymous
        )
    }

    // callbackFlow is a flow builder that lets you convert callback-based APIs into flows
    // can used for getting authentication state
    // help responds to changes in the user's sign-in state at runtime
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
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }


    /** use try and catch when using this methods or USE CoroutineExceptionHandler !!!*/
    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    //TODO: Delete user posts
    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}