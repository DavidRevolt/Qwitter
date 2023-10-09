package com.davidrevolt.qwitter.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.davidrevolt.qwitter.core.designsystem.icons.QwitterIcons

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    //TODO: change it like nia app
    LOGIN(
        selectedIcon = QwitterIcons.Login,
        unselectedIcon = QwitterIcons.Login,
        iconTextId = com.davidrevolt.feature.login.R.string.login,
        titleTextId = com.davidrevolt.feature.login.R.string.login,
    ),

    HOME(
        selectedIcon = QwitterIcons.Home,
        unselectedIcon = QwitterIcons.Home,
        iconTextId = com.davidrevolt.feature.home.R.string.home,
        titleTextId = com.davidrevolt.feature.home.R.string.home,
    )
}