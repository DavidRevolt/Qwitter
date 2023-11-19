package com.davidrevolt.qwitter.core.data.modelmappers

import android.net.Uri
import com.davidrevolt.qwitter.core.model.Tweet
import com.davidrevolt.qwitter.core.model.User
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseTweet
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseUser

/**
 *  Map between models defined in different modules.
 */
suspend fun NetworkFirebaseTweet.asExternalModel(userIdToUser: suspend (String) -> NetworkFirebaseUser?) =
    Tweet(
        uid = uid,
        user =  userIdToUser(userId)?.asExternalModel() ?: User(),
        content = content,
        mediaUri = mediaUri.map { Uri.parse(it) },
        comments = emptyList(),
        likedBy = emptyList(),
        publishDate = publishDate
    )

