package com.davidrevolt.qwitter.core.editprofile

import android.net.Uri
import android.util.Log
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

    fun onSetDisplayNameClick(userName: String) {
        viewModelScope.launch {
            userDataRepository.setDisplayName(userName)
        }
    }

    fun onSetProfilePictureClick(img: Uri) {
        viewModelScope.launch {
            try{
                userDataRepository.setProfilePicture(img)
            }
            catch (e:Exception){
                Log.d("AppLog", "${e.toString()}")
            }

        }
    }
}


sealed interface EditProfileUiState {
    data class UserData(val user: User) : EditProfileUiState
    object Loading : EditProfileUiState
}