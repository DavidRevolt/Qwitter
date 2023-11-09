package com.davidrevolt.qwitter.core.network

import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseTweet
import kotlinx.coroutines.flow.Flow

/**
 *  Firebase offline persistence is enabled by default.
 */
interface QwitterNetworkDataSource {
    /**
     * @param userId userId who creates the tweet(normally should done in server side).
     * @param content tweet content.
     * @param mediaUri Uri's for media.
     */
    suspend fun createTweet(networkFirebaseTweet: NetworkFirebaseTweet)

    fun getAllTweets(): Flow<List<NetworkFirebaseTweet>>
}