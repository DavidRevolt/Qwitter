package com.davidrevolt.qwitter.core.network

import android.net.Uri

interface QwitterNetworkStorageSource {

    /**
     * @param imgUri local storage uri to profile picture.
     * @param uid the user unique id.
     * @return https path to profile picture.
     */
    suspend fun uploadProfilePicture(imgUri: Uri, uid: String) :Uri

}