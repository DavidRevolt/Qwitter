package com.davidrevolt.qwitter.core.data.repository

import android.net.Uri
import android.util.Log
import com.davidrevolt.qwitter.core.data.modelmappers.asExternalModel
import com.davidrevolt.qwitter.core.model.User
import com.davidrevolt.qwitter.core.network.QwitterNetworkDataSource
import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSource
import com.davidrevolt.qwitter.core.network.model.NetworkFirebaseUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val networkDataSource: QwitterNetworkDataSource,
    private val networkStorage: QwitterNetworkStorageSource
) :
    UserDataRepository {

    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()


    override fun getCurrentUser(): Flow<User> =
        networkDataSource.getCurrentUser(currentUserId).map { it?.asExternalModel() ?: User() }

    override suspend fun getUser(uid:String): User =
        networkDataSource.getUser(uid)?.asExternalModel() ?: User()


    override suspend fun setDisplayName(displayName: String) {
        Log.i("AppLog", "1. Start setDisplayName")
        networkDataSource.setDisplayName(currentUserId, displayName)
        Log.i("AppLog", "2. setDisplayName Done!")
    }

    override suspend fun setProfilePicture(imgUri: Uri) {
        Log.i("AppLog", "1. Start setProfilePicture")
        val networkUri = networkStorage.uploadProfilePicture(imgUri, currentUserId)
        Log.i("AppLog", "2. Network Uri is: $networkUri")
        networkDataSource.setProfilePicture(currentUserId, networkUri.toString())
        Log.i("AppLog", "3. setProfilePicture Done!")
    }

    override suspend fun initCurrentUserDataCollection() {
        Log.i("AppLog", "1. Init User Data Collection")
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            networkDataSource.initUserDataCollection(
                NetworkFirebaseUser(
                    uid = currentUser.uid,
                    displayName = currentUser.displayName ?: "",
                    profilePictureUri = currentUser.photoUrl.toString()
                )
            )
        }
        Log.i("AppLog", "2. Init User Data Collection Done!")
    }


}