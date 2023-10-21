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
    HOME(
        selectedIcon = QwitterIcons.Home,
        unselectedIcon = QwitterIcons.Home,
        iconTextId = com.davidrevolt.feature.home.R.string.home,
        titleTextId = com.davidrevolt.feature.home.R.string.home,
    )
}