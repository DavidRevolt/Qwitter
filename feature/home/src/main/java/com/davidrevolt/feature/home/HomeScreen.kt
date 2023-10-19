package com.davidrevolt.feature.home


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidrevolt.qwitter.core.designsystem.components.CoilImageReq
import com.davidrevolt.qwitter.core.designsystem.components.QwitterTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    //TODO: use this in lazycolumn modifier
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val profileUiState by viewModel.profileUiState.collectAsStateWithLifecycle()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "In Home Screen", fontSize = 16.sp)

        when (profileUiState) {
            is ProfileUiState.UserData -> {
                val it = profileUiState as ProfileUiState.UserData
                Log.d("AppLog", it.data.displayName)
                Log.d("AppLog", it.data.uid)
                Log.d("AppLog", it.data.profilePicture.toString())


              //  it.data.profilePicture.let { it1 -> CoilImageReq(modifier = Modifier.clip(CircleShape), imgUri = it1) }


                QwitterTopAppBar(
                    profilePicture = { CoilImageReq(modifier = Modifier.clip(CircleShape), imgUri = it.data.profilePicture) },
                    onProfileClick = {},
                    scrollBehavior = scrollBehavior
                )


            }
            is ProfileUiState.Loading ->  Log.d("AppLog","Loading")
        }

    }


}