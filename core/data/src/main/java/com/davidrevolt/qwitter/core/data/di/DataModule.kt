package com.davidrevolt.qwitter.core.data.di

import com.davidrevolt.qwitter.core.data.repository.UserRepository
import com.davidrevolt.qwitter.core.data.repository.UserRepositoryImpl
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitorImpl
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindsSnackbarManager(snackbarManagerImpl: SnackbarManagerImpl): SnackbarManager

    @Binds
    abstract fun bindsNetworkMonitorService(networkMonitorImpl: NetworkMonitorImpl): NetworkMonitor

    @Binds
    abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}