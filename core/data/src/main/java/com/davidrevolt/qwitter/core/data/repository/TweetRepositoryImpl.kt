package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import com.davidrevolt.qwitter.core.data.modelmappers.asExternalModel
import com.davidrevolt.qwitter.core.model.Tweet
import com.davidrevolt.qwitter.core.network.QwitterNetworkDataSource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TweetRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val networkDataSource: QwitterNetworkDataSource
) : TweetRepository {
    override suspend fun createTweet(content: String, mediaUri: List<Uri>) =
        networkDataSource.createTweet(
            userId = firebaseAuth.currentUser!!.uid,
            content = content,
            mediaUri = mediaUri.map { it.toString() }
        )


    override fun getAllTweets(): Flow<List<Tweet>> =
        networkDataSource.getAllTweets().map {
            it.map { networkTweet -> networkTweet.asExternalModel(networkDataSource::getUser) }
        }


}