package com.davidrevolt.qwitter.core.network

import android.util.Log
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseTweet
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TWEETS_COLLECTION = "tweets"
const val USERS_DATA_COLLECTION = "users"

/**
 *  Firebase offline persistence is enabled by default.
 */
class QwitterNetworkDataSourceImpl @Inject constructor(private val firestore: FirebaseFirestore) :
    QwitterNetworkDataSource {

    override fun getAllTweets(): Flow<List<NetworkFirebaseTweet>> =
        firestore.collection(TWEETS_COLLECTION)
            .orderBy("publishDate", Query.Direction.DESCENDING)
            .dataObjects()

    override suspend fun createTweet(userId: String, content: String, mediaUri: List<String>) {
        val tweet = NetworkFirebaseTweet(
            userId = userId, // Ref 2 user doc
            content = content,
            mediaUri = mediaUri
        )
        firestore.collection(TWEETS_COLLECTION).add(tweet).await()
    }


    override fun getCurrentUser(uid: String): Flow<NetworkFirebaseUser?> {
        return firestore.collection(USERS_DATA_COLLECTION).document(uid).dataObjects()
    }

    override suspend fun getUser(uid: String): NetworkFirebaseUser? {
        Log.d("AppLog2","getUser with uid: $uid")
        if (uid.isEmpty()) return null
        return firestore.collection(USERS_DATA_COLLECTION).document(uid).get().await().toObject()
    }

    override suspend fun initUserDataCollection(networkFirebaseUser: NetworkFirebaseUser) {
        firestore.collection(USERS_DATA_COLLECTION).document(networkFirebaseUser.uid)
            .set(networkFirebaseUser, SetOptions.merge()).await()
    }

    override suspend fun setProfilePicture(uid: String, imgUri: String) {
        firestore.collection(USERS_DATA_COLLECTION).document(uid)
            .update("profilePictureUri", imgUri).await()
    }

    override suspend fun setDisplayName(uid: String, displayName: String) {
        firestore.collection(USERS_DATA_COLLECTION).document(uid)
            .update("displayName", displayName).await()
    }
}