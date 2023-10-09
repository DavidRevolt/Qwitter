package com.davidrevolt.feature.login

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.davidrevolt.qwitter.core.designsystem.drawables.login_logo
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

@Composable
fun LoginScreen(
    onSuccessLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val signInIntent = viewModel::provideSignInIntent
    val signInLauncher = rememberLauncherForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        signInResult(res, onSuccessLogin)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = login_logo), contentDescription = "login_logo",
        )

        Button(
            onClick = {
                signInLauncher.launch(signInIntent())
            },

            ) {
            Text(text = "Login/Signup", fontSize = 16.sp)
        }
    }


}

private fun signInResult(result: FirebaseAuthUIAuthenticationResult, onSuccessLogin: () -> Unit) {
    val response = result.idpResponse
    if (result.resultCode == ComponentActivity.RESULT_OK) {
        onSuccessLogin()
    } else {
        // TODO: add crashlytics
    }
}