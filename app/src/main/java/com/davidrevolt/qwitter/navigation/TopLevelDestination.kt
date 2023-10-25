package com.davidrevolt.qwitter.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.davidrevolt.qwitter.core.designsystem.drawables.qwitter_logo
import com.davidrevolt.qwitter.core.designsystem.icons.QwitterIcons

enum class TopLevelDestination(
    // BottomBar Props
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,

    // AppBar Props
    val title: @Composable () -> Unit,
) {

    HOME(
        selectedIcon = QwitterIcons.Home,
        unselectedIcon = QwitterIcons.Home,
        iconTextId = com.davidrevolt.feature.home.R.string.home,
        title = {
            Image(
                painter = painterResource(id = qwitter_logo), contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    )
}