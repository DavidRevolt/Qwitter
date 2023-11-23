package com.davidrevolt.qwitter.core.designsystem.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.davidrevolt.qwitter.core.designsystem.R
import kotlinx.coroutines.launch

@Composable
fun QwitterNavigationDrawer(
    drawerState: DrawerState,
    profilePictureUri: Uri?,
    onProfilePictureClick: () -> Unit,
    onSignOutClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet() {
                ImageLoader(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = {
                            scope.launch {
                                drawerState.close()
                                onProfilePictureClick.invoke()
                            }
                        })
                        .size(45.dp),
                    imgUri = profilePictureUri
                )
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = stringResource(R.string.sign_out))},
                    selected = false,
                    onClick = {scope.launch {
                        drawerState.close()
                        onSignOutClick.invoke()
                    }}
                )
                // ...other drawer items
            }
        },
        drawerState = drawerState, content = content
    )
}

@Composable
private fun onItemClick(onSignOutClick: () -> Unit) {
    val scope = rememberCoroutineScope()
}