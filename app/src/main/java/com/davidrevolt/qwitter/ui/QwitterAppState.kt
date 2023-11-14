package com.davidrevolt.qwitter.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.davidrevolt.feature.home.HOME_ROUTE
import com.davidrevolt.feature.home.navigateToHome
import com.davidrevolt.feature.login.navigateToLogin
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.data.utils.authentication.AuthenticationService
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch


@Composable
fun rememberQwitterAppState(
    authenticationService: AuthenticationService,
    networkMonitor: NetworkMonitor,
    userDataRepository: UserDataRepository,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): QwitterAppState {
    return remember(
        networkMonitor,
        authenticationService,
        userDataRepository,
        navController,
        coroutineScope
    ) {
        QwitterAppState(
            networkMonitor,
            authenticationService,
            userDataRepository,
            navController,
            coroutineScope,
        )
    }
}

class QwitterAppState(
    networkMonitor: NetworkMonitor,
    private val authenticationService: AuthenticationService,
    userDataRepository: UserDataRepository,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope
) {

    // Internet Connection
    val isOffline = networkMonitor.isOnline()
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false,
        )


    // Fetch CurrentUser data from db only if authState[user is logged in] is true
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentUser = authenticationService.authState().transformLatest { authState ->
        Log.i("AppLog", "AuthState is: $authState")

        when (authState) {
            true -> {
                userDataRepository.getCurrentUser().collect { emit(it) }
            }

            false -> {
                emit(null)
            }
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )


    // Navigation
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HOME_ROUTE -> TopLevelDestination.HOME
            else -> null
        }

    // Showing scaffold app bars only if we in TopLevelDestination screen
    val shouldShowAppBars: Boolean
        @Composable get() = currentTopLevelDestination != null


    /**
     * UI logic for navigating to a top level destination in the app without looping.
     * Top level destinations have only one copy of the destination of the back stack,
     * and save and restore state whenever you navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselect the same item
            launchSingleTop = true
            // Restore state when reselect a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.HOME -> navController.navigateToHome(topLevelNavOptions)
        }

    }

    fun signOut(){
        coroutineScope.launch {
            authenticationService.signOut()
            onAuthStateChangeNavigation()
        }

    }
    /**
     * Clearing the whole backstack and then navigating to destination according to Auth state.
     * Not logged in -> navigate to Login screen.
     * Logged in -> navigate to Home screen.
     */
    fun onAuthStateChangeNavigation() {
        val topLevelNavOptions = navOptions {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true

        }
        if (authenticationService.userLoggedIn)
            navController.navigateToHome(topLevelNavOptions)
        else
            navController.navigateToLogin(topLevelNavOptions)
    }

}