package com.davidrevolt.qwitter.core.network.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class NetworkFirebaseUser(
    @DocumentId
    val uid: String = "",
    val displayName :String = "",
    val profilePictureUri: String = "",
    @ServerTimestamp
    val joinDate: Date = Timestamp.now().toDate()
)