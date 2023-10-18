package com.davidrevolt.qwitter.core.data.utils.authentication

interface AuthenticationService {
    val userLoggedIn: Boolean

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun deleteAccount()
    suspend fun signOut()
}