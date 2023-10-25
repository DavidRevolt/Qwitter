package com.davidrevolt.qwitter.core.network

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val PROFILE_STORAGE = "profile_pictures"
class QwitterNetworkStorageSourceImpl  @Inject constructor(private val firebaseStorage : FirebaseStorage)
    :QwitterNetworkStorageSource{


    /**
     * @param imgPath local storage uri to profile picture
     * @param uid the user unique id
     * @return https path to profile picture
     * */
    override suspend fun uploadProfilePicture(imgPath: Uri, uid: String): Uri {
        val storageRef = firebaseStorage.reference
        val profilePicturesRef = storageRef.child("$PROFILE_STORAGE/$uid")
        return profilePicturesRef.putFile(imgPath).await().storage.downloadUrl.await()
    }

}