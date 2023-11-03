package com.davidrevolt.feature.home

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val snackbarManager: SnackbarManager
) : ViewModel() {


    val profileUiState = userDataRepository.currentUser.map(ProfileUiState::UserData)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState.Loading
    )

    fun buttonTest(msg:String){
        viewModelScope.launch {
            snackbarManager.snackbarHostState.showSnackbar(
                message = msg,
                actionLabel = null,
                duration = SnackbarDuration.Short,
            )
        }
    }
}


sealed interface ProfileUiState {
    data class UserData(val user: User) : ProfileUiState
    object Loading : ProfileUiState
}