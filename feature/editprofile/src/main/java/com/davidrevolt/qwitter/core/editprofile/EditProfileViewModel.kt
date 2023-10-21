package com.davidrevolt.qwitter.core.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    val profileUiState = userDataRepository.currentUser.map(EditProfileUiState::UserData)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EditProfileUiState.Loading
        )

    fun onUpdateDisplayNameClick(userName: String){
        viewModelScope.launch {
            userDataRepository.updateDisplayName(userName)
        }
    }
}


sealed interface EditProfileUiState {
    data class UserData(val user: User) : EditProfileUiState
    object Loading : EditProfileUiState
}