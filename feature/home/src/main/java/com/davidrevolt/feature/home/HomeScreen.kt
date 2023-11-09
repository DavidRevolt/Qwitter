package com.davidrevolt.feature.home


import android.util.Log
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

    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarMangerTest = viewModel::snackbarMangerTest
    val tweetTest = viewModel::tweetTest
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is HomeUiState.Data -> {
                val data = (uiState as HomeUiState.Data)
                val tweets = data.tweets
                tweets.forEach{t->   Log.d("AppLog","${t}")}

                Button(onClick = {scope.launch{onShowSnackbar("Test",null)}},content = {Text("Test onShowSnackbar")})
                Button(onClick = { snackbarMangerTest("TEST") },content = {Text("Test snackbar Manger")})
                Button(onClick = { tweetTest("Tweet Content") },content = {Text("Test Tweet")})
            }
            is HomeUiState.Loading -> LoadingWheel()
        }
    }
}
