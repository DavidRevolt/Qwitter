package com.davidrevolt.qwitter.core.model

import android.net.Uri
import java.util.Date

data class Tweet(
    val id: String,
    val userId: String,
    val content: String,
    val mediaUri: List<Uri>,
    val comments: List<Tweet>,
    val likedBy: List<User>,
    val timestamp: Date
)