package com.davidrevolt.feature.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidrevolt.qwitter.core.designsystem.components.CoilImageReq
import com.davidrevolt.qwitter.core.designsystem.components.LoadingWheel
import com.davidrevolt.qwitter.core.designsystem.components.QwitterTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()

    //TODO: use this in lazycolumn modifier
    // val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is ProfileUiState.UserData -> {

                val user = (uiState as ProfileUiState.UserData).user
                QwitterTopAppBar(
                    profilePicture = {
                        CoilImageReq(
                            modifier = Modifier.clip(CircleShape).clickable { onProfileClick.invoke() },
                            imgUri = user.profilePictureUri
                        )
                    },
                    //   scrollBehavior = scrollBehavior
                )
                Text(text = "Hello ${user.displayName}", fontSize = 16.sp)
            }

            is ProfileUiState.Loading -> LoadingWheel()
        }

    }
}
