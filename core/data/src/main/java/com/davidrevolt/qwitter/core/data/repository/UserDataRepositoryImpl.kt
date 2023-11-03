package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import android.util.Log
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


    /**
     * Flow Update only when:
     * Right after the listener has been registered
     * When a user is signed in
     * When the current user is signed out
     * When the current user changes
     * When auth.currentUser?.reload()
     */
    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    channel.trySend(auth.currentUser?.let {
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
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        firebaseAuth.currentUser?.updateProfile(profileUpdates)?.await()

    }

    override suspend fun setProfilePicture(imgUri: Uri) {
        Log.d("AppLog", "1. Start setProfilePicture")
        val networkUri = networkStorage.uploadProfilePicture(imgUri, currentUserId)
        Log.d("AppLog", "3. Network Uri is: $networkUri")
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(networkUri)
            .build()
        firebaseAuth.currentUser?.updateProfile(profileUpdates)?.await()
        Log.d("AppLog", "4. setProfilePicture Done!")
    }
}