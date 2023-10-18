package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import com.davidrevolt.qwitter.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val currentUserId: String
   // val currentUser: User?
    val currentUser: Flow<User>

    suspend fun updateDisplayName(displayName: String)
    suspend fun uploadProfilePicture(photoUri: Uri)

}