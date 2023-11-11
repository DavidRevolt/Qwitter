package com.davidrevolt.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.designsystem.drawables.login_logo
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userDataRepository: UserDataRepository,) : ViewModel() {

    private val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

    fun provideSignInIntent() = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(login_logo)
        .build()

    fun createCurrentUserDataCollection()=
        viewModelScope.launch {
            userDataRepository.initCurrentUserDataCollection()
        }

}