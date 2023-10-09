package com.davidrevolt.qwitter.core.auth.di

import com.davidrevolt.qwitter.core.auth.AuthenticationService
import com.davidrevolt.qwitter.core.auth.AuthenticationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationServiceModule {
    @Binds
    abstract fun bindsAuthenticationService(authenticationServiceImpl: AuthenticationServiceImpl): AuthenticationService
}