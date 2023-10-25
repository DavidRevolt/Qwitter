package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import com.davidrevolt.qwitter.core.model.User
import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val networkStorage: QwitterNetworkStorageSource
) :
    UserDataRepository {

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

    // callbackFlow is a flow builder that lets you convert callback-based APIs into flows
    // can used for getting authentication state, responds to changes in the user's sign-in state at runtime
    /**
     * Flow Update only when:
     * Right after the listener has been registered
     * When a user is signed in
     * When the current user is signed out
     * When the current user changes
     * When auth.currentUser?.reload()?.await()
     */
    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                       trySend(auth.currentUser?.let {
                            User(
                                it.uid,
                                if (it.displayName != null) it.displayName!! else "",
                                if (it.photoUrl != null) it.photoUrl!! else Uri.EMPTY
                            )
                        } ?: User())

                }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }

    override suspend fun setDisplayName(displayName: String) {
        //  throw RuntimeException()
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        firebaseAuth.currentUser?.updateProfile(profileUpdates)?.await()

    }

    override suspend fun setProfilePicture(img: Uri) {
        val networkUri = networkStorage.uploadProfilePicture(img, currentUserId)
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(networkUri)
            .build()
        firebaseAuth.currentUser?.updateProfile(profileUpdates)?.await()
       // firebaseAuth.currentUser?.reload()?.await()
    }
}