package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import com.davidrevolt.qwitter.core.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    UserDataRepository {

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()

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
                            if (it.photoUrl != null) it.photoUrl!! else Uri.EMPTY
                        )
                    } ?: User())
                }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }

    override  suspend fun updateDisplayName(displayName: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()
        firebaseAuth.currentUser?.updateProfile(profileUpdates)
    }

    override suspend fun uploadProfilePicture(photoUri: Uri) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(photoUri)
            .build()
        firebaseAuth.currentUser?.updateProfile(profileUpdates)
    }

}