package com.davidrevolt.qwitter.core.auth

import kotlinx.coroutines.flow.Flow

interface AuthenticationService {

    val currentUserId: String?
    val userLoggedIn: Boolean
    val currentUser: Flow<User>
    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}

data class User(
    val uid: String = "",
    val displayName :String = "",
    val isAnonymous: Boolean = true
)