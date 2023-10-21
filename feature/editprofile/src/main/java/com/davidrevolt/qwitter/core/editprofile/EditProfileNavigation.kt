package com.davidrevolt.qwitter.core.editprofile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val EDIT_PROFILE_ROUTE = "edit_profile_route"

fun NavController.navigateToEditProfile(navOptions: NavOptions? = null) {
    this.navigate(EDIT_PROFILE_ROUTE, navOptions)
}

fun NavGraphBuilder.editProfileScreen() {
    composable(route = EDIT_PROFILE_ROUTE) {
        EditProfileScreen()
    }
}