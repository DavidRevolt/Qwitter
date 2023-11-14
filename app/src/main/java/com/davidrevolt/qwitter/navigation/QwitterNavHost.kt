package com.davidrevolt.qwitter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.davidrevolt.feature.home.HOME_ROUTE
import com.davidrevolt.feature.home.homeScreen
import com.davidrevolt.feature.login.LOGIN_ROUTE
import com.davidrevolt.feature.login.loginScreen
import com.davidrevolt.qwitter.core.data.utils.authentication.AuthenticationService
import com.davidrevolt.qwitter.core.editprofile.editProfileScreen
import com.davidrevolt.qwitter.core.editprofile.navigateToEditProfile
import com.davidrevolt.qwitter.ui.QwitterAppState


@Composable
fun QwitterNavigation(
    appState: QwitterAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    authenticationService: AuthenticationService
) {
    val navController = appState.navController
    val startDestination = if (authenticationService.userLoggedIn) HOME_ROUTE else LOGIN_ROUTE

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginScreen(
            onSuccessLogin = appState::onAuthStateChangeNavigation,
            onShowSnackbar = onShowSnackbar,
        )
        homeScreen(onShowSnackbar = onShowSnackbar)
        editProfileScreen(onBackClick = navController::popBackStack)
    }
}