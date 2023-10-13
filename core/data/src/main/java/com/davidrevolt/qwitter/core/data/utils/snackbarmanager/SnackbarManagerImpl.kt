package com.davidrevolt.qwitter.core.data.utils.snackbarmanager

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import javax.inject.Inject

class SnackbarManagerImpl @Inject constructor() : SnackbarManager {

    override val snackbarHostState = SnackbarHostState()

    override suspend fun showSnackbar(
        message: String,
        action: String?,
        duration: SnackbarDuration
    ): Boolean {
        return snackbarHostState.showSnackbar(
            message = message,
            actionLabel = action,
            duration = SnackbarDuration.Short,
        ) == SnackbarResult.ActionPerformed
    }
}