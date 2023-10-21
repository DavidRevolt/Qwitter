package com.davidrevolt.qwitter.ui

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.davidrevolt.qwitter.R
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.data.utils.authentication.AuthenticationService
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.designsystem.components.CoilImageReq
import com.davidrevolt.qwitter.core.designsystem.components.QwitterBottomNavigationBar
import com.davidrevolt.qwitter.core.designsystem.components.QwitterNavigationBarItem
import com.davidrevolt.qwitter.core.designsystem.components.QwitterTopAppBar
import com.davidrevolt.qwitter.core.editprofile.navigateToEditProfile
import com.davidrevolt.qwitter.navigation.QwitterNavigation
import com.davidrevolt.qwitter.navigation.TopLevelDestination

@Composable
fun QwitterApp(
    authenticationService: AuthenticationService,
    networkMonitor: NetworkMonitor,
    snackbarManager: SnackbarManager,
    userDataRepository: UserDataRepository,
    appState: QwitterAppState = rememberQwitterAppState(
        authenticationService = authenticationService,
        networkMonitor = networkMonitor,
        userDataRepository = userDataRepository
    )
) {

    val snackbarHostState = snackbarManager.snackbarHostState
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val notConnectedMessage = stringResource(R.string.no_internet_connection)
    val currentUser by appState.currentUser.collectAsStateWithLifecycle()

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = SnackbarDuration.Indefinite,
            )
        }
    }

    Scaffold(
        topBar = {
            if (appState.shouldShowAppBars) {
                appState.currentTopLevelDestination?.let {
                    QwitterTopBar(
                        title = it.title,
                        profilePictureUri = currentUser?.profilePictureUri,
                        appState.navController::navigateToEditProfile
                    )
                }
            }
        },
        containerColor = Color.Transparent,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            if (appState.shouldShowAppBars) {
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
                onShowSnackbar = { message, action ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = SnackbarDuration.Short,
                    ) == SnackbarResult.ActionPerformed
                },
                authenticationService = authenticationService
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
    QwitterBottomNavigationBar(
        modifier = modifier
    ) {
        destinations.forEach { destination ->
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QwitterTopBar(
    title: @Composable () -> Unit,
    profilePictureUri: Uri?,
    onProfilePictureClick: () -> Unit,
) {
    QwitterTopAppBar(
        title = title,
        profilePicture = {
            if (profilePictureUri != null) {
                CoilImageReq(
                    modifier = Modifier.clip(CircleShape).clickable { onProfilePictureClick.invoke() },
                    imgUri = profilePictureUri
                )
            }
        }
    )
}