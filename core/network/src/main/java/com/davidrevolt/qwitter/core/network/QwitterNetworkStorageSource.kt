package com.davidrevolt.qwitter.core.network

import android.net.Uri

interface QwitterNetworkStorageSource {

    suspend fun uploadProfilePicture(imgPath: Uri ) :Uri
}