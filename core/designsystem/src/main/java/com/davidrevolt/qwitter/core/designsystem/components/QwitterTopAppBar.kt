package com.davidrevolt.qwitter.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.davidrevolt.qwitter.core.designsystem.drawables.qwitter_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QwitterTopAppBar(
    title: @Composable () -> Unit = appBarLogo,
    profilePicture: @Composable () -> Unit,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    onProfileClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = title,
        navigationIcon =
            // Profile Pic
            profilePicture
        ,
        actions = {
            // Settings Button
            if(actionIcon != null){
                IconButton(onClick = onActionClick) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = actionIconContentDescription,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        colors = colors,
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}


val appBarLogo: @Composable () -> Unit = {
    Image(
        painter = painterResource(id = qwitter_logo), contentDescription = "login_logo",
        modifier = Modifier.size(35.dp))
}