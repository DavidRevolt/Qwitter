package com.davidrevolt.qwitter.core.model

import android.net.Uri

data class Tweet(
    val creator: User,
    val content: String,
    val image: Uri?,
    var likes: Int,
    var retweets: Int,
    val comments: Int,
    val timestamp: Long
)