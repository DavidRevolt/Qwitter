package com.davidrevolt.qwitter.core.data.repository

import com.davidrevolt.qwitter.core.model.Tweet
import kotlinx.coroutines.flow.Flow

interface TweetRepository {
    suspend fun createTweet(content: String, mediaUri: List<String> = emptyList())

    fun getAllTweets(): Flow<List<Tweet>>
}