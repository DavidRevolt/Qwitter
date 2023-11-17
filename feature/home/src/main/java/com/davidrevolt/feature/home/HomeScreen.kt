package com.davidrevolt.feature.home


import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidrevolt.qwitter.core.designsystem.components.LoadingWheel
import com.davidrevolt.qwitter.core.designsystem.components.TweetDisplay
import com.davidrevolt.qwitter.core.model.Tweet


@Composable
fun HomeScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is HomeUiState.Data -> {
                val tweetTest = viewModel::tweetTest
                Button(onClick = { tweetTest("Tweet Content") }, content = { Text("Test Tweet") })
                val data = (uiState as HomeUiState.Data)
                HomeScreenContent(data.tweets)
            }

            is HomeUiState.Loading -> LoadingWheel()
        }
    }
}


@Composable
private fun HomeScreenContent(tweets: List<Tweet>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
       // contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 20.dp)
    ) {
        tweets.forEach { tweet ->
            Log.d("AppLog", "$tweet")
            item {
                TweetDisplay(
                    modifier = Modifier,
                    displayName = "DisplayName",
                    profilePictureUri = Uri.EMPTY,
                    content = tweet.content,
                    mediaUri = tweet.mediaUri,
                    commentsCount = tweet.comments.size,
                    likedByCount = tweet.likedBy.size,
                    publishDate = tweet.publishDate
                )
            }
        }
    }
    TweetDisplay()
    tweets.forEach { t -> Log.d("AppLog", "${t}") }
}