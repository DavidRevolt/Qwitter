package com.davidrevolt.qwitter.core.network.di

import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSource
import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSourceImpl
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    // Binding
    @Binds
    abstract fun bindsQwitterNetworkStorageSource(qwitterNetworkStorageSourceImpl: QwitterNetworkStorageSourceImpl): QwitterNetworkStorageSource

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
    }
}