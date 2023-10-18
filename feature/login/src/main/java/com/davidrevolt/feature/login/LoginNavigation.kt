package com.davidrevolt.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    this.navigate(LOGIN_ROUTE,navOptions)
}

fun NavGraphBuilder.loginScreen(
    onSuccessLogin: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable(route = LOGIN_ROUTE) {
        LoginScreen(
            onSuccessLogin = onSuccessLogin,
            onShowSnackbar = onShowSnackbar
        )
    }
}