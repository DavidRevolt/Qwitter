package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import com.davidrevolt.qwitter.core.model.Tweet
import kotlinx.coroutines.flow.Flow

interface TweetRepository {
    suspend fun createTweet(content: String, mediaUri: List<Uri> = emptyList())
    fun getAllTweets(): Flow<List<Tweet>>
}