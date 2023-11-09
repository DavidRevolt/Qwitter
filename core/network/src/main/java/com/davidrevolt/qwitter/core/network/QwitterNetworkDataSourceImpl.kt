package com.davidrevolt.qwitter.core.network

import android.util.Log
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseTweet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val TWEETS_COLLECTION = "tweets"
/**
 *  Firebase offline persistence is enabled by default.
 */
class QwitterNetworkDataSourceImpl@Inject constructor(private val firestore: FirebaseFirestore): QwitterNetworkDataSource {
    override suspend fun createTweet(networkFirebaseTweet: NetworkFirebaseTweet) {
        Log.i("AppLog","QwitterNetworkDataSource: Creating a Tweet")
        firestore.collection(TWEETS_COLLECTION).add(networkFirebaseTweet).await()
    }

    override fun getAllTweets(): Flow<List<NetworkFirebaseTweet>> =
        firestore.collection(TWEETS_COLLECTION).dataObjects()

}