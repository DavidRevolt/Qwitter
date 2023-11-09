package com.davidrevolt.qwitter.core.data.modelmappers

import android.net.Uri
import com.davidrevolt.qwitter.core.model.Tweet
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseTweet

/**
 *  Map between models defined in different modules.
 */
fun NetworkFirebaseTweet.asExternalModel() =
    Tweet(
        id = id,
        userId = userId,
        content = content,
        mediaUri = mediaUri.map { it -> Uri.parse(it) },
        comments = emptyList(),
        likedBy = emptyList(),
        timestamp = timestamp
    )
