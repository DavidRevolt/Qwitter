package com.davidrevolt.feature.login

import androidx.lifecycle.ViewModel
import com.davidrevolt.qwitter.core.designsystem.drawables.login_logo
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )


    fun provideSignInIntent() = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(login_logo)
        .build()

}
