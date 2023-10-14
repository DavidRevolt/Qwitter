package com.davidrevolt.qwitter.core.data.utils.snackbarmanager

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState


/**
 * Utility for accessing snackbarHostState from ViewModels, etc.
 */


interface SnackbarManager {

     val snackbarHostState: SnackbarHostState

    suspend fun showSnackbar(
        message: String,
        action: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ): Boolean
}