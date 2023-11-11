package com.davidrevolt.qwitter.core.editprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidrevolt.qwitter.core.data.repository.UserDataRepository
import com.davidrevolt.qwitter.core.data.utils.analytics.Analytics
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val analytics: Analytics,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val editProfileUiState = combine(_isRefreshing, userDataRepository.getCurrentUser()){
            isRefreshing, currentUser -> EditProfileUiState.Data(isRefreshing, currentUser)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = EditProfileUiState.Loading)

    fun onSetDisplayNameClick(userName: String) {
        safeLauncher(_isRefreshing) {
            userDataRepository.setDisplayName(userName)
        }
    }

    fun onSetProfilePictureClick(img: Uri) {
        safeLauncher(_isRefreshing) {
             userDataRepository.setProfilePicture(img)
           }
    }


    private fun safeLauncher(state: MutableStateFlow<Boolean>, block: suspend CoroutineScope.() -> Unit){
        state.value = true
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                analytics.logException(throwable)
                snackbarManager.showSnackbar("${throwable.message}", null)
            }
        }, block = block).invokeOnCompletion {
            state.value = false
        }}


}


sealed interface EditProfileUiState {
    data class Data(val isRefreshing: Boolean, val user: User ) : EditProfileUiState
    object Loading : EditProfileUiState
}
