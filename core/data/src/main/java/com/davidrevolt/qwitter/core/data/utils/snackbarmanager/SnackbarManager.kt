package com.davidrevolt.qwitter.core.data.utils.snackbarmanager

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState


/**
 * Utility for posting messages from ViewModels, etc.
 * It init message as StateFlow
 * Should be collected in AppState and post to snackbarHost
 */


interface SnackbarManager {

    val snackbarHostState: SnackbarHostState
    suspend fun showSnackbar(
        message: String,
        action: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ): Boolean
}