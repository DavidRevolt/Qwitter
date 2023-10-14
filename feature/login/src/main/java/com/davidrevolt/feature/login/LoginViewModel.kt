package com.davidrevolt.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.data.repository.UserRepository
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.designsystem.drawables.login_logo
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val snackbarManager: SnackbarManager
) : ViewModel() {

    private val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build()
    )


    fun provideSignInIntent() = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(login_logo)
        .build()


    fun showSnackbar(message: String) {
        viewModelScope.launch {
            snackbarManager.showSnackbar(message)
        }
    }


}
