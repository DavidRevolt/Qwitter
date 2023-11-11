package com.davidrevolt.qwitter.core.data.modelmappers

import android.net.Uri
import com.davidrevolt.qwitter.core.model.User
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseUser

/**
 *  Map between models defined in different modules.
 */
fun NetworkFirebaseUser.asExternalModel() =
    User(
        uid = uid,
        displayName = displayName,
        profilePictureUri = Uri.parse(profilePictureUri),
        joinDate = joinDate
    )