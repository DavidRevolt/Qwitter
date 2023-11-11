package com.davidrevolt.qwitter.core.model

import android.net.Uri
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

data class User(
    val uid: String = "",
    val displayName :String = "",
    val profilePictureUri: Uri = Uri.EMPTY,
    val joinDate: Date = Timestamp.from(Instant.now())
)