package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import com.davidrevolt.qwitter.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val currentUserId: String
    val currentUser: Flow<User>

    suspend fun setDisplayName(displayName: String)
    suspend fun setProfilePicture(imgUri: Uri)

}