package com.davidrevolt.qwitter.core.network

import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseTweet
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseUser
import kotlinx.coroutines.flow.Flow

/**
 *  Firebase offline persistence is enabled by default.
 */
interface QwitterNetworkDataSource {

    fun getAllTweets(): Flow<List<NetworkFirebaseTweet>>
    suspend fun createTweet(networkFirebaseTweet: NetworkFirebaseTweet)

    /**
     * Fetch user data from DB.
     * @param uid user Id
     * @return Flow of NetworkFirebaseUser.
     */
    fun getCurrentUser(uid: String): Flow<NetworkFirebaseUser?>

    /**
     * Creates a user document in Firestore collection whenever a new user is created in Firebase Auth.
     * Normally we don't do this in client side but in server side via Extension "Firestore User Document".
     * Reason: We can't use Firebase Auth if we want to fetch other user(not the current one) data.
     */
    suspend fun initUserDataCollection(networkFirebaseUser: NetworkFirebaseUser)
    suspend fun setProfilePicture(uid: String, imgUri: String)
    suspend fun setDisplayName(uid: String, displayName: String)

    suspend fun getUser(uid: String): NetworkFirebaseUser?
}