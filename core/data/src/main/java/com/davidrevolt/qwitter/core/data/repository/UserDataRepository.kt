package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import com.davidrevolt.qwitter.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val currentUserId: String

    /**
     * Getting the current logged in user
     * Make sure user is logged in by using AuthenticationService AuthState or UserLoggedIn
     * */
    fun getCurrentUser(): Flow<User>
    suspend fun getUser(uid:String): User
    suspend fun setDisplayName(displayName: String)
    suspend fun setProfilePicture(imgUri: Uri)

    /**
     * Creates a user document in Firestore collection whenever a new user is created in Firebase Auth.
     * Normally we don't do this in client side but in server side via Extension "Firestore User Document".
     * Reason: We can't use Firebase Auth if we want to fetch other user(not the current one) data.
     */
    suspend fun initCurrentUserDataCollection()

}