package com.davidrevolt.qwitter.core.data.di

import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.data.repository.UserDataRepositoryImpl
import com.davidrevolt.qwitter.core.data.utils.authentication.AuthenticationService
import com.davidrevolt.qwitter.core.data.utils.authentication.AuthenticationServiceImpl
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitorImpl
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManagerImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule { // switch to interface if only using @Binds without companion object

    // Binding
    @Binds
    abstract fun bindsUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository


    // Utils
    @Binds
    abstract fun bindsSnackbarManager(snackbarManagerImpl: SnackbarManagerImpl): SnackbarManager

    @Binds
    abstract fun bindsNetworkMonitorService(networkMonitorImpl: NetworkMonitorImpl): NetworkMonitor

    @Binds
    abstract fun bindsAuthenticationService(authenticationServiceImpl: AuthenticationServiceImpl): AuthenticationService

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    }
}