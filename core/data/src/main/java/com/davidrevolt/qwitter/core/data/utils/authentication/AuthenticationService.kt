package com.davidrevolt.qwitter.core.data.utils.authentication

import kotlinx.coroutines.flow.Flow

interface AuthenticationService {
    val userLoggedIn: Boolean

    /**
     * @Return Flow<Boolean> if user is logged in
     * */
    fun authState(): Flow<Boolean>
    suspend fun authenticate(email: String, password: String)
    suspend fun register(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}