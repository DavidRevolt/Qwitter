package com.davidrevolt.qwitter.core.model

import android.net.Uri

data class User(
    val uid: String = "",
    val displayName :String = "", // Gmail full name
    val profilePictureUri: Uri = Uri.EMPTY, // Gmail profile pic
)