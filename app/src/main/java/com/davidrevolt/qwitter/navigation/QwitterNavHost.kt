package com.davidrevolt.qwitter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.davidrevolt.feature.home.HOME_ROUTE
import com.davidrevolt.feature.home.homeScreen
import com.davidrevolt.feature.login.LOGIN_ROUTE
import com.davidrevolt.feature.login.loginScreen
import com.davidrevolt.qwitter.core.data.repository.UserRepository
import com.davidrevolt.qwitter.ui.QwitterAppState


@Composable
fun QwitterNavigation(
    appState: QwitterAppState,
    userRepository: UserRepository
) {
    val navController = appState.navController
    val startDestination = if(userRepository.userLoggedIn) HOME_ROUTE else LOGIN_ROUTE

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginScreen(onSuccessLogin = { appState.clearBackStackAndNavigate(TopLevelDestination.HOME) })
        homeScreen(onProfileClick ={})
    }
}
