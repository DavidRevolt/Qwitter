package com.davidrevolt.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidrevolt.qwitter.core.designsystem.components.QwitterTopAppBar
import com.davidrevolt.qwitter.core.designsystem.icons.QwitterIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    //TODO: use this in lazycolumn modifier
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QwitterTopAppBar(
            title = { Text("X") },
            profileIcon = QwitterIcons.Person,
            onProfileClick = {},
            scrollBehavior= scrollBehavior
        )
        Text(text = "In Home Screen", fontSize = 16.sp)
    }

}