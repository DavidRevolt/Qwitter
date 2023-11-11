package com.davidrevolt.qwitter.core.network

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.time.Instant
import javax.inject.Inject

const val USERS_STORAGE = "users"
const val PROFILE_STORAGE = "profile_pictures"

class QwitterNetworkStorageSourceImpl @Inject constructor(private val firebaseStorage: FirebaseStorage) :
    QwitterNetworkStorageSource {

    override suspend fun uploadProfilePicture(imgUri: Uri, uid: String): Uri {
        val storageRef = firebaseStorage.reference
        val profilePicturesRef =
            storageRef.child("$USERS_STORAGE/$uid/$PROFILE_STORAGE/${Instant.now()}")
        return profilePicturesRef.putFile(imgUri).await().storage.downloadUrl.await()
    }

}