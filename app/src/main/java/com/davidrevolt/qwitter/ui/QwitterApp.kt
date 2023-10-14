package com.davidrevolt.qwitter.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.davidrevolt.qwitter.core.data.repository.UserRepository
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.designsystem.components.QwitterNavigationBar
import com.davidrevolt.qwitter.core.designsystem.components.QwitterNavigationBarItem
import com.davidrevolt.qwitter.navigation.QwitterNavigation
import com.davidrevolt.qwitter.navigation.TopLevelDestination

@Composable
fun QwitterApp(
    userRepository: UserRepository,
    networkMonitor: NetworkMonitor,
    snackbarManager: SnackbarManager,
    appState: QwitterAppState = rememberQwitterAppState(
        userRepository = userRepository,
        networkMonitor = networkMonitor
    )
) {

    val snackbarHostState = snackbarManager.snackbarHostState
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val notConnectedMessage = stringResource(R.string.no_internet_connection)


    //TODO: should move all to Qwitter Background
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
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
        ) {
            QwitterNavigation(
                appState = appState,
                userRepository = userRepository
            )
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
        destinations.filter { it != TopLevelDestination.LOGIN }.forEach { destination ->
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
                label = { Text(stringResource(destination.iconTextId)) },
                alwaysShowLabel = true
            )
        }
    }
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false