package com.davidrevolt.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.data.repository.TweetRepository
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.model.Tweet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tweetRepository: TweetRepository,
    private val snackbarManager: SnackbarManager
) : ViewModel() {


    val homeUiState = tweetRepository.getAllTweets().map(HomeUiState::Data)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState.Loading
    )

    fun tweetTest(content:String){
        viewModelScope.launch {
            tweetRepository.createTweet(content)
        }
    }
}


sealed interface HomeUiState {
    data class Data(val tweets: List<Tweet>) : HomeUiState
    object Loading : HomeUiState
}