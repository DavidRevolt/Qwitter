package com.davidrevolt.qwitter.core.data.repository

import com.davidrevolt.qwitter.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val userLoggedIn: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}