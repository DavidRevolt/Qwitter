package com.davidrevolt.qwitter.core.network

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val PROFILE_STORAGE = "profile_img"
class QwitterNetworkStorageSourceImpl  @Inject constructor(private val firebaseStorage : FirebaseStorage)
    :QwitterNetworkStorageSource{
    override suspend fun uploadProfilePicture(imgPath: Uri): Uri {
        val storageRef = firebaseStorage.reference
        return storageRef.putFile(imgPath).await().storage.downloadUrl.await()
    }

}