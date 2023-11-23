package com.davidrevolt.qwitter.core.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QwitterTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actionIcon: @Composable() (RowScope.() -> Unit) ={},
    onNavigationIconClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        title = title,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                navigationIcon()
            }
        },
        actions = {
            // Settings Button
            IconButton(onClick = onActionClick) {
                actionIcon()
            }
        },
        colors = colors,
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}

