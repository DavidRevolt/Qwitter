package com.davidrevolt.qwitter.core.data.di

import androidx.compose.material3.SnackbarHostState
import com.davidrevolt.qwitter.core.data.repository.TweetRepository
import com.davidrevolt.qwitter.core.data.repository.TweetRepositoryImpl
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.data.repository.UserDataRepositoryImpl
import com.davidrevolt.qwitter.core.data.utils.analytics.Analytics
import com.davidrevolt.qwitter.core.data.utils.analytics.AnalyticsImpl
import com.davidrevolt.qwitter.core.data.utils.authentication.AuthenticationService
import com.davidrevolt.qwitter.core.data.utils.authentication.AuthenticationServiceImpl
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitorImpl
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManagerImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule { // switch to interface if only using @Binds without companion object

    // Repos
    @Binds
    abstract fun bindsUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository

    @Binds
    abstract fun bindsTweetRepository(tweetRepositoryImpl: TweetRepositoryImpl): TweetRepository


    // Utils
    @Binds
    abstract fun bindsAnalytics(analyticsImpl: AnalyticsImpl): Analytics

    @Binds
    abstract fun bindsSnackbarManager(snackbarManagerImpl: SnackbarManagerImpl): SnackbarManager

    @Binds
    abstract fun bindsNetworkMonitor(networkMonitorImpl: NetworkMonitorImpl): NetworkMonitor

    @Binds
    abstract fun bindsAuthenticationService(authenticationServiceImpl: AuthenticationServiceImpl): AuthenticationService

    companion object {
        @Provides
        @Singleton
        fun provideSnackbarHostState(): SnackbarHostState = SnackbarHostState()

        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

        @Provides
        @Singleton
        fun provideFirebaseCrashlytics(): FirebaseCrashlytics = Firebase.crashlytics
    }
}