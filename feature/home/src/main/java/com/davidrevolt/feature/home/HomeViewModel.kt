package com.davidrevolt.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {


    val profileUiState = userDataRepository.currentUser.map(ProfileUiState::UserData)
        .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState.Loading
    )

}


sealed interface ProfileUiState {
    data class UserData(val user: User) : ProfileUiState
    object Loading : ProfileUiState
}