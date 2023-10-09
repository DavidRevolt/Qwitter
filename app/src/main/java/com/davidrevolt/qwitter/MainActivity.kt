package com.davidrevolt.qwitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.davidrevolt.qwitter.core.auth.AuthenticationService
import com.davidrevolt.qwitter.core.data.utils.networkmonitor.NetworkMonitor
import com.davidrevolt.qwitter.core.data.utils.snackbarmanager.SnackbarManager
import com.davidrevolt.qwitter.ui.QwitterApp
import com.davidrevolt.qwitter.ui.theme.QwitterTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authenticationService: AuthenticationService

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var snackbarManager: SnackbarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val locale = Locale("en")
            Locale.setDefault(locale)

            QwitterTheme {
                QwitterApp(authenticationService, networkMonitor, snackbarManager)
            }
        }
    }


}


