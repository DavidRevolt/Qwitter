package com.davidrevolt.qwitter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.davidrevolt.qwitter.R
import com.davidrevolt.qwitter.core.auth.AuthenticationService
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.designsystem.components.QwitterNavigationBar
import com.davidrevolt.qwitter.core.designsystem.components.QwitterNavigationBarItem
import com.davidrevolt.qwitter.navigation.QwitterNavigation
import com.davidrevolt.qwitter.navigation.TopLevelDestination

@Composable
fun QwitterApp(
    authenticationService: AuthenticationService,
    networkMonitor: NetworkMonitor,
    snackbarManager: SnackbarManager,
    appState: QwitterAppState = rememberQwitterAppState(
        authenticationService = authenticationService,
        networkMonitor = networkMonitor
    )
) {

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val notConnectedMessage = stringResource(R.string.not_connected)
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarManager.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    //TODO: should move to Qwitter Background
    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(
                snackbarManager.snackbarHostState,
                modifier = Modifier.safeDrawingPadding()
            )
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                QwitterBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .safeDrawingPadding()
        ) {
            QwitterNavigation(
                appState = appState,
                authenticationService = authenticationService)
        }
    }
}


@Composable
private fun QwitterBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    QwitterNavigationBar(
        modifier = modifier
    ) {
        destinations.filter{ it != TopLevelDestination.LOGIN}.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            QwitterNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) }
            )
        }
    }
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false