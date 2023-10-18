package com.davidrevolt.qwitter.core.network.di

import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSource
import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSourceImpl
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Provides
import javax.inject.Singleton

abstract class NetworkModule { // might switch to interface

    // Binding
    @Binds
    abstract fun bindsQwitterNetworkStorageSource(qwitterNetworkStorageSourceImpl: QwitterNetworkStorageSourceImpl): QwitterNetworkStorageSource

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
    }
}