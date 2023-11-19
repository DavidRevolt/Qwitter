package com.davidrevolt.qwitter.core.network.model


import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date


data class NetworkFirebaseTweet(
    @DocumentId
    val uid: String = "", // Firebase automatically populated with the document's ID
    val userId: String = "",
    val content: String = "",
    val mediaUri: List<String> = emptyList(),
    val comments: List<NetworkFirebaseTweet> = emptyList(),
    val likedBy: List<String> = emptyList(),
    @ServerTimestamp
    val publishDate: Date = Timestamp.now()
        .toDate()  // @ServerTimestamp-annotated field, it will be replaced with a server-generated timestamp
)
