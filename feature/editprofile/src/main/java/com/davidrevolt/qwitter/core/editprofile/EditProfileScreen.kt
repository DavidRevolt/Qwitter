package com.davidrevolt.qwitter.core.editprofile

import android.net.Uri
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidrevolt.qwitter.core.designsystem.components.CoilImageReq
import com.davidrevolt.qwitter.core.designsystem.components.LoadingWheel
import com.davidrevolt.qwitter.core.designsystem.components.QwitterTopAppBar
import com.davidrevolt.qwitter.core.designsystem.icons.QwitterIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(viewModel: EditProfileViewModel = hiltViewModel()) {

    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val onUpdateDisplayNameClick = viewModel::onUpdateDisplayNameClick
    //TODO: use this in lazycolumn modifier
    // val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })}
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is EditProfileUiState.UserData -> {

                val user = (uiState as EditProfileUiState.UserData).user
                QwitterTopAppBar(
                    profilePicture = {
                        CoilImageReq(
                            modifier = Modifier.clip(CircleShape),
                            imgUri = user.profilePictureUri
                        )
                    },
                    onProfileClick = {},
                    //   scrollBehavior = scrollBehavior
                )
                EditProfileContent(
                    displayName = user.displayName,
                    profilePictureUri = user.profilePictureUri,
                    onUpdateDisplayNameClick = onUpdateDisplayNameClick,
                    onUpdateProfilePictureClick = {},
                    isRefreshing = isRefreshing
                )
            }

            is EditProfileUiState.Loading -> LoadingWheel()
        }

    }
}


@Composable
private fun EditProfileContent(
    displayName: String,
    profilePictureUri: Uri,
    onUpdateDisplayNameClick: (String) -> Unit,
    onUpdateProfilePictureClick: () -> Unit,
    isRefreshing: Boolean
) {

    DisplayNameForm(onUpdateDisplayName = onUpdateDisplayNameClick, placeholder = displayName)
}


@Composable
private fun DisplayNameForm(onUpdateDisplayName: (String) -> Unit, placeholder: String) {
    var text by remember { mutableStateOf(placeholder) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var error by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Display Name") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = { Icon(QwitterIcons.Person, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = {
            if (text.isNotEmpty()) {
                onUpdateDisplayName(text)
                // Hide the keyboard after submitting the search
                keyboardController?.hide()
                //or hide keyboard
                focusManager.clearFocus()
            } else {
                error = true
            }

        }),
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedBorderColor = Color.Blue,
            focusedContainerColor = Color.White.copy(alpha = 0.5f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(15.dp),
        isError = error
    )
}