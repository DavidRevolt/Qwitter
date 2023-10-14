package com.davidrevolt.qwitter.core.model

data class User(
    val uid: String = "",
    val displayName :String = "",
    val isAnonymous: Boolean = true
)