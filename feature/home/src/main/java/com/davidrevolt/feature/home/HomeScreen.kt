package com.davidrevolt.feature.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidrevolt.qwitter.core.designsystem.components.LoadingWheel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val btnTest = viewModel::buttonTest
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is ProfileUiState.UserData -> {
                val user = (uiState as ProfileUiState.UserData).user
                Text(text = "Hello ${user.displayName}", fontSize = 16.sp)
                Button(onClick = {scope.launch{onShowSnackbar("Test",null)}},content = {Text("Test snackbar screen")})
                Button(onClick = { btnTest("TEST") },content = {Text("Test snackbar viewModel")})
            }
            is ProfileUiState.Loading -> LoadingWheel()
        }
    }
}
