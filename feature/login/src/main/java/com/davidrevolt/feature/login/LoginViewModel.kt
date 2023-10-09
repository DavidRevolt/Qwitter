package com.davidrevolt.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.auth.AuthenticationService
import com.davidrevolt.qwitter.core.designsystem.drawables.login_logo
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ActionCodeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
) : ViewModel() {

    val currentUser = authenticationService.currentUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private val actionCodeSettings = ActionCodeSettings.newBuilder()
        .setAndroidPackageName("com.davidrevolt.qwitter", /*installIfNotAvailable*/true, /*minimumVersion*/
            null
        )
        .setHandleCodeInApp(true)
        .setUrl("https://google.com") // This URL needs to be allowlisted
        .build()

    private val providers = arrayListOf(
/*        AuthUI.IdpConfig.EmailBuilder().enableEmailLinkSignIn()
            .setActionCodeSettings(actionCodeSettings).build(),*/
        AuthUI.IdpConfig.GoogleBuilder().build()

    )


    fun provideSignInIntent() = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(login_logo)
        .build()


    fun onSignOut() {
        viewModelScope.launch {
            authenticationService.signOut()
        }
    }

    fun userLoggedIn() = authenticationService.userLoggedIn

}
