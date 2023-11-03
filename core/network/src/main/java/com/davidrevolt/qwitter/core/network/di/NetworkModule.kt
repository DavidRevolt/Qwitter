package com.davidrevolt.qwitter.core.network.di

import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSource
import com.davidrevolt.qwitter.core.network.QwitterNetworkStorageSourceImpl
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
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

/*    @Binds
    abstract fun bindsQwitterNetworkDataSource(qwitterNetworkDataSourceImpl: QwitterNetworkDataSourceImpl): QwitterNetworkDataSource*/

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage
    }
}