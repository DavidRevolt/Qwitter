package com.davidrevolt.qwitter.core.editprofile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidrevolt.qwitter.core.designsystem.components.ImageLoader
import com.davidrevolt.qwitter.core.designsystem.components.LoadingWheel
import com.davidrevolt.qwitter.core.designsystem.components.QwitterTopAppBar
import com.davidrevolt.qwitter.core.designsystem.components.clearImageLoaderCache
import com.davidrevolt.qwitter.core.designsystem.icons.QwitterIcons


@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current
    val uiState by viewModel.profileUiState.collectAsStateWithLifecycle()
    val onSetDisplayNameClick = viewModel::onSetDisplayNameClick
    val onSetProfilePictureClick = viewModel::onSetProfilePictureClick

    Column(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .fillMaxSize(),
           // .safeDrawingPadding()
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (uiState) {
            is EditProfileUiState.UserData -> {

                val user = (uiState as EditProfileUiState.UserData).user
                EditProfileContent(
                    displayName = user.displayName,
                    profilePictureUri = user.profilePictureUri,
                    onSetDisplayNameClick = onSetDisplayNameClick,
                    onSetProfilePictureClick = onSetProfilePictureClick,
                    onBackClick = onBackClick
                )
            }

            is EditProfileUiState.Loading -> LoadingWheel()
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileContent(
    displayName: String,
    profilePictureUri: Uri,
    onSetDisplayNameClick: (String) -> Unit,
    onSetProfilePictureClick:(Uri) -> Unit,
    onBackClick: () -> Unit,
) {
    QwitterTopAppBar(
        title = { Text("Edit Profile") },
        navigationIcon = {Icon(imageVector = QwitterIcons.ArrowBack, contentDescription ="Back" )},
        actionIcon = { Text("Save") },
        onNavigationIconClick = onBackClick,
       // onActionClick = onSaveClick
    )

    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            clearImageLoaderCache(context)
            Log.d("AppLog", "Selected URI: $uri")
            onSetProfilePictureClick(uri)


        } else {
            Log.d("AppLog", "No media selected")
        }
    }

    ImageLoader(modifier = Modifier
        .padding(50.dp)
        .clip(CircleShape)
        .size(200.dp)
        .clickable {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },imgUri = profilePictureUri)
    DisplayNameForm(placeholder = displayName, onSetDisplayNameClick=onSetDisplayNameClick)
}




@Composable
private fun DisplayNameForm(placeholder: String,onSetDisplayNameClick: (String) -> Unit ) {
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
                onSetDisplayNameClick(text)
                keyboardController?.hide()
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